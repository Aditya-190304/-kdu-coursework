const fs = require('fs');
const path = require('path');

function writereport(filepath, content) {
    const dir = path.dirname(filepath);
    if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir, { recursive: true });
    }
    fs.writeFileSync(filepath, content, "utf-8");
}

function generateSummaryReport(employees, outputPath){
    const totalemployees=employees.length;

    let totalsalary=0;
    const departments={};
    employees.forEach(element => {
        totalsalary+=element.salary;
        if(!departments[element.department]){
            departments[element.department]={
                count:0,
                totalsalary:0
            };
        }
        departments[element.department].count+=1;
        departments[element.department].totalsalary+=element.salary;
    });

    let avgsalary;
    if(totalemployees===0)
    {
        avgsalary=0;
    }
    else{
        avgsalary=totalsalary/totalemployees;
    }

    let report="";
    report+=`total employees: ${totalemployees}\n`;
    report+=`total salary: ${totalsalary}\n`;
    report+=`average salary: ${avgsalary}\n\n`;

    report+=`department wise breakdown:\n`

    for(let dept in departments){
        const count=departments[dept].count;
        const depttotal=departments[dept].totalsalary;
        const deptavg=depttotal/count;

        report += `\ndepartment: ${dept}\n`;
        report += `count: ${count}\n`;
        report += `total salary: ${depttotal}\n`;
        report += `average salary: ${deptavg}\n`;
    }
    writereport(outputPath,report);
}

function generateDepartmentReport(employees,department,outputPath)
{
    let report = "";
    const deptemployees=employees.filter(e=>e.department===department);

    let totalsalary=0;
    deptemployees.forEach(element=>{
        totalsalary+=element.salary;
    });

    let avgsalary;
    if(deptemployees.length===0)
    {
        avgsalary=0;
    }
    else{
        avgsalary=totalsalary/deptemployees.length;
    }

    report+=`depatment: ${department}\n`
    report+=`employee count: ${deptemployees.length}\n\n`

    deptemployees.forEach(e=>{
        report+=`${e.name}-${e.salary}\n`
    });

    report += `\ntotal salary: ${totalsalary}\n`;
    report += `average salary: ${avgsalary}\n`;

    writereport(outputPath,report);
}

function generateTopEarnersReport(employees, count, outputPath){

    const sorted=[...employees].sort((a,b)=>b.salary-a.salary);

    const top=[];
    for(let i=0 ; i<count && i<sorted.length; i++)
    {
        top.push(sorted[i]);
    }

    let report="";
    top.forEach((e,index)=>{
        report+=`${index+1}->${e.name}->${e.department}->${e.salary}\n`;
    });

    writereport(outputPath,report);
}

module.exports = {
    writereport,
    generateSummaryReport,
    generateDepartmentReport,
    generateTopEarnersReport
};

