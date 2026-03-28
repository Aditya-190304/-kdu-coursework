//Task 4.1: Employee Analytics 

const { employees} = require("./task1");
function  getAnalytics(employees) {
    return employees.reduce((skillmap,emp)=>{
        for(let i=0;i<emp.skills.length;i++){
            const skill=emp.skills[i];
            skillmap[skill]=(skillmap[skill] || 0)+1;  
        }
        return skillmap;
    },{});
}