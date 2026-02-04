import { Controller,Delete,Get,Param,Post,Body } from '@nestjs/common';
import { TodosService } from './todos.service';
import { CreateTodoDto } from './dto/create-todo.dto';
@Controller('todos')
export class TodosController {
    constructor(private readonly todosService: TodosService) {}

    @Post()
    create(@Body() dto: CreateTodoDto) {
        return this.todosService.create(dto);
    }

    @Get()
    findAll() {
        return this.todosService.findall();
    }

    @Get(':id')
    findOne(@Param('id') id: string) {
        return this.todosService.findone(id);
    }

    @Delete(':id')
    remove(@Param('id') id: string) {
        return this.todosService.remove(id);
    }
}
