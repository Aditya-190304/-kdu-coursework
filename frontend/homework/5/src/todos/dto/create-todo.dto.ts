import { IsNotEmpty,IsOptional,IsString,MaxLength,MinLength } from "class-validator";

export class CreateTodoDto {
   @IsString()
   @IsNotEmpty()
   @MinLength(3)
   @MaxLength(100)
   title: string;
   @IsOptional()
   @IsString()
   @MaxLength(500)
   description?: string;
}