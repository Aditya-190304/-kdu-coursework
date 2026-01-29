const { employee1,employee2,employee3,employee4,employee5,employees} = require("./task1");


//Task 3.1: Object Destructuring

function empdetails(emp)
{
    const{name,department,salary}=emp;
    return `name: ${name}, department: ${department}, salary: ${salary}`;
}

// Task 3.2: Array Destructuring

function salrycomp(employees)
{
    const sorted=employees.sort((a,b)=>b.salary - a.salary);
    const[toppaid]=sorted;
    const bottompaid=sorted[sorted.length-1];
    return {toppaid,bottompaid};
}

//Task 3.3: Spread Operator

function mergeskills(emp1,emp2)
{
    const skills1=[...emp1.skills,...emp2.skills];
    const skills2=new Set(skills1);
    return skills2;
}

// Task 3.4: Rest Operator

function totalcount(...employee)
{
    return employee.length;
}

function averageage(employees)
{
    const totalage=employees.reduce((sum,emp)=>sum+emp.age,0);
    return totalage/employees.length;
}

