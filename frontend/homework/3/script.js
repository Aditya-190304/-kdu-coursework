function  Task(title,  priority)  { 
this.id  =  Date.now();
this.title  =  title; 
this.priority  =  priority; 
this.completed  =  false; 
}

Task.prototype.markComplete = function()  {
	this.completed  =  true; 
	return  this;
}

Task.prototype.updatePriority = function(newPriority) {
	if (newPriority === "low" || newPriority === "medium" || newPriority === "high") {
		this.priority = newPriority;
	}
	return this;
}

Task.prototype.getinfo=function()  {
	return  `id: ${this.id}, task:  ${this.title},  priority:  ${this.priority},  completed:  ${this.completed}`; 
}

function PriorityTask(title, priority, dueDate) {
	Task.call(this, title, priority);
	this.dueDate = dueDate;
}

PriorityTask.prototype = Object.create(Task.prototype);
PriorityTask.prototype.constructor = PriorityTask;

PriorityTask.prototype.getinfo = function() {
	let info = `id: ${this.id}, task:  ${this.title},  priority:  ${this.priority},  completed:  ${this.completed}`;
	if (this.dueDate) {
		info += `, due date: ${this.dueDate}`;
	}
	return info;
}


Task.prototype.getAllTasksInfo = function(tasksArray) {
    return tasksArray.map(task => this.getinfo.call(task));
};


function  createTaskAsync(title, priority)  {  
    console.log("Creating task...");
    return new Promise(resolve,reject=>{
        setTimeout(()=>{
            console.log("Task created.");
            const task1 = new Task(title, priority);
            resolve(task1);
        },1000);
    })
}

// i did this because we will run code->microtask->macrotask
function demonstrateEventLoop() {
  setTimeout(() => {
    console.log("1");
    Promise.resolve().then(() => {
      setTimeout(() => {
        console.log("4");
    }, 2000);
      setTimeout(() => {
        console.log("3"); 
        setTimeout(() => {
          console.log("2");
        }, 2000);
      }, 4000);
    });
  }, 2000);
}


async function  createAndSaveTask(title, priority){
    try{
        const task2 = await createTaskAsync(title, priority);
        const task3=await createTaskAsync(task2.title, task2.priority);
        console.log("Task created and saved successfully!");
        return task3;
    }
    catch(error){
        console.error("Error creating or saving task:", error);
    }
}

async function createMultipleTasksAsync(taskDataArray){
    console.log(`creating ${taskDataArray.length} tasks...`);
    const task4=Promise.all(
        taskDataArray.map(data=>createTaskAsync(data.title, data.priority))
    );
    console.log("all tasks created!");
    return task4;
}

