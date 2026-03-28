// Task 1.1: Create Employee Objects
//Task 1.3: Object Methods (getFullInfo())

const employee1 = { 
  id:  1, 
  name:  "Alice  Johnson", 
  age:  30, 
  salary:  85000, 
  department:  "Engineering", 
  skills:  ["JavaScript",  "React"], 
  experience:  5, 
  getfullinfo(){
    return `name: ${this.name}, department: ${this.department}, salary: ${this.salary}`;
  }
};

const employee2 = { 
  id:  2, 
  name:  "Aditya Nigam", 
  age:  22, 
  salary:  75000, 
  department:  "Marketing",
  skills:  ["HTML",  "CSS",  "Node.js"], 
  experience:  5 ,
    getfullinfo(){
    return `name: ${this.name}, department: ${this.department}, salary: ${this.salary}`;
  }
};

const employee3 = {
    id: 3,
    name: "Ojas Sharma",
    age: 25,
    salary: 90000,
    department: "Sales",
    skills: ["Communication", "Negotiation", "CRM"," Presentation"],
    experience: 6,
      getfullinfo(){
    return `name: ${this.name}, department: ${this.department}, salary: ${this.salary}`;
  }
};

const employee4 = {
    id: 4,
    name: "Deepak Yadav",
    age: 23,
    salary: 80000,
    department: "HR",
    skills: ["Leadership", "Team Management", "Recruitment"],
    experience: 7,
     getfullinfo(){
    return `name: ${this.name}, department: ${this.department}, salary: ${this.salary}`;
  }
};

const employee5 = {
    id: 5,
    name: "Kritik Agarwal",
    age: 24,
    salary: 95000,
    department: "Finance",
    skills: ["Accounting", "Excel",],
    experience: 4,
      getfullinfo(){
    return `name: ${this.name}, department: ${this.department}, salary: ${this.salary}`;
  }
};

// Task 1.2: Access and Modify Object Properties 

function  getEmployeeInfo(emp)
{
    return  `name:  ${emp.name},  department:  ${emp.department},  salary:  ${emp.salary}`;
} 

function addSkill(emp, skill) {
    emp.skills.push(skill);
}
//Task 1.3: Object Methods
// compareEmployees(emp1, emp2)

function compareEmployees(emp1, emp2) {
    if(emp1.skills.length > emp2.skills.length) {
        return emp1.name;
    } else if(emp1.skills.length < emp2.skills.length) {
        return emp2.name;
    } else {
        return "both emp have same number of skills";
    }
}

//Task 2.1: Create Employee Array 

const employees=[employee1, employee2, employee3, employee4, employee5];

module.exports={employees,employee1,employee2,employee3,employee4,employee5};


