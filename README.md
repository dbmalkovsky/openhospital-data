# Open Hospital - Data

<span style="color:red">*** This is a work in progress ***</span>

The purpose is to generate random data for Open Hospital and either directly 
persist the data to the database or output Java constructor like representations 
of the objects.   

The main class is: [org.isf.generate.Generate](https://github.com/dbmalkovsky/openhospital-data/blob/master/src/main/java/org/isf/generate/Generate.java)

Currently only [patient](https://github.com/informatici/openhospital-core/blob/develop/src/main/java/org/isf/patient/model/Patient.java) objects are generated and when the persist flag is false
(the default) the output looks like:

    new Patient("Lucy", "Carver", LocalDate.parse("1925-09-01"), 96, "", 'F', "145 Nakapiripirit St.", "Mayuge", 
    "", "0664160200", "Eva", 'D', "John", 'U', "O-", 'N', 'U', "74201589422", "Single", "Other")

The locale for the patients can be set as well as dqn:
* the number of patients to generate
* the sex of the generate patient
* the minimum age of the generate patient
* the maximum age of the generated patient
* the percent of the patiens that are admitted
* the percent of the patient that are discharged

So to generate 4 male paients in the age range of 13 to 24 where 50% are admitted 
and 25% are dischared the following Java method can be used:

    generatePatient(4, male(), 13, 24, 50, 25);