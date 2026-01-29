const { employees} = require("./task1");
//Task 2.1: Create Employee Array 
console.log(employees);

//Task 2.2: Array Methods – Filter 

function  filterByExperience(employees, minExperience) {
 return employees.filter(emp=> emp.experience>=minExperience);
}

//Task 2.3: Array Methods – Map

function getsummaries(employees) {
     return employees.map(emp => `${emp.name} (${emp.department}) – $${emp.salary}`);
}

//Task 2.4: Array Methods – Reduce

function averageSalary(employees) {
const totalSalary = employees.reduce((sum, emp) => sum + emp.salary, 0);
    return totalSalary / employees.length;
}

function departmentcount(employees){
    return employees.reduce((deptmap,emp)=>{
        deptmap[emp.department]=(deptmap[emp.department] || 0)+1;
        return deptmap;
    },{})
}

//Task 2.5: Array Methods – Find and Sort

function highestsalary(employees){
    return employees.reduce((maxEmp, emp) => emp.salary > maxEmp.salary ? emp : maxEmp);
}

function sortemployeesbyexp(employees){
return employees.sort((a, b) => b.experience - a.experience);
}


