import { Injectable ,NotFoundException} from '@nestjs/common';
import { CreateTodoDto } from './dto/create-todo.dto';
import { Todo } from './entities/todo.entity';
import { TodosStore } from './store/todos.store';
import { v4 as uuidv4 } from 'uuid';

@Injectable()
export class TodosService {
    constructor(private readonly todosStore: TodosStore) {}

    create(createTodoDto: CreateTodoDto): Todo {
        const now=new Date();

        const todo: Todo = {
            id:uuidv4(),
            title:createTodoDto.title,
             description:createTodoDto.description,
            completed:false,
           createdat:now,
            updatedat:now
        };
        this.todosStore.add(todo);
         return todo;
    }
    findall(): Todo[] {
       return this.todosStore.getAll();
    }

    findone(id: string): Todo {
        const todo = this.todosStore.findById(id);
         if(!todo) {
            throw new NotFoundException(`Todo not found`);
    }
    return todo;
    }
    remove(id: string): void {
        const removed = this.todosStore.removebyId(id);
       if(!removed) {
            throw new NotFoundException(`Todo not found`);
        }
    }
}


