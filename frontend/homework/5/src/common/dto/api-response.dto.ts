export class apiresponsedto<T>{
    data?: T;
    success: boolean
    message?: string;

    constructor(params:{success: boolean; data?: T; message?: string}){
        this.success=params.success;
        this.data=params.data;
        this.message=params.message;
    }
}