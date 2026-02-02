const path=require("path");
const {readfile}=require("./src/filereader")

const{
    generateSummaryReport,
    generateDepartmentReport,
    generateTopEarnersReport}=require("./src/reportgenerator");

const employees=readfile(path.join(__dirname,"data","employees.json"));

const command=process.argv[2];
const value=process.argv[3];

const summary=path.join(__dirname,"reports","summary.txt");
const toppath=path.join(__dirname,"reports","top-earners.txt");

if(!command)
{
    generateSummaryReport(employees,summary);
    generateTopEarnersReport(employees,5,toppath);
    generateDepartmentReport(employees, "Engineering", "reports/Engineering.txt");
}
else if(command==='summary'){
    generateSummaryReport(employees,summary);
    console.log("generated summary report");
}

else if(command==='department')
{
    const dept=value;
    if(!dept)
    {
        console.log('give in this format [node index.js department "HR"');
    }
    else{
        generateDepartmentReport(
            employees,dept,path.join(__dirname,"reports",`${dept}.txt`)
        );
          console.log(` department report for ${dept}.`);
    }
}
else if(command==='top')
{
        let count;
        if (value) {
        count = Number(value);
        }
        else {
        count = 5;
        }
        generateTopEarnersReport(employees,count,toppath);
        console.log(`report for top ${count}earners`);
}
else
{
    console.log("Invalid command.");
}