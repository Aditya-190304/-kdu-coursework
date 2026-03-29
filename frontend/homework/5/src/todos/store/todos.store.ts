import { Injectable } from "@nestjs/common";
import { Todo } from "../entities/todo.entity";

@Injectable()

export class TodosStore {
    private readonly todos: Todo[] = [];

    getAll(): Todo[] {
        return this.todos;
}
add(todo: Todo): void {
    this.todos.push(todo);
}
findById(id: string): Todo | undefined {
    return this.todos.find(t => t.id === id);
}
removebyId(id: string): boolean {
    const index = this.todos.findIndex(t => t.id === id);
    if(index === -1) {
        return false;
    }
    this.todos.splice(index, 1);
    return true;
}
}