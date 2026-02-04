import { Module } from '@nestjs/common';
import { TodosController } from './todos.controller';
import { TodosService } from './todos.service';
import { TodosStore } from './store/todos.store';

@Module({
  controllers: [TodosController],
  providers: [TodosService, TodosStore],
})
export class TodosModule {}
