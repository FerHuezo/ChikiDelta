CREATE TABLE tbEnfermero ( 
    UUID_Enfermero VARCHAR2(100), 
    Nombre_Enfermero VARCHAR2(100), 
    Edad_Enfermero INT, 
    Peso_Enfermero NUMBER, 
    Correo_Enfermero VARCHAR2(100)
);

UPDATE tbEnfermero SET UUID_Enfermero = SYS_GUID();

SELECT * FROM tbEnfermero;