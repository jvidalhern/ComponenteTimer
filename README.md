# ComponenteTimer
Tarea 3 de Desarrollo de Interfaces (DI) para la titulación DAM

La tarea consiste en la creación de un componente que pueda ser reutilizable en cualquier proyecto Java. Este componente realiza una cuenta atrás de los minutos y segundos indicados por el usuario. Como tal no cuenta con inputs propios gráficos, de modo que puede probarse realizando un pequeño script o simplemente descomentar el método main dentro de la clase TemporizadorComponent:

Al hacerlo el fichero será ejecutable:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/e8b69959-a3a0-4dd7-b4d1-ae92600dac8e)

Cómo puede verse en el código crea un botón de "Iniciar Temporizador" que lo pondrá en marcha con los valores directamente seteados:

public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TemporizadorComponent");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 100);

            TemporizadorComponent temporizador = new TemporizadorComponent("", "7", true, true);
            frame.add(temporizador);

            JButton startButton = new JButton("Iniciar Temporizador");
            startButton.addActionListener(e -> {
                temporizador.iniciarCuentaAtras("", "7", true, "3");
            });

            frame.add(startButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

Una vez ejecuta vemos una pequeña interfaz gráfica:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/308483d6-5626-4118-9924-d2b0483c57f1)

Ejecutará 3 rondas de 7 segundos con los sonidos de inicio y fin activados:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/c77b2774-7f73-4f32-af5e-cfeeb83d1589)

También se dispone de una clase llamada PantallaPrincipal, dónde ahora si podremos configurar gráficamente el número de minutos, segundos y si los sonidos están activos para dos temporizadores:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/4c85fef4-819a-4a69-b476-51eabc4e767c)

La interfaz prevendrá al usuario de introducir carácteres que no sean números enteros:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/243220e3-c1de-4d79-8c93-dd882d7343d4)

Y se asegurará de que los valores varían entre 1 y 60 para segundos, y entre 1 y 20 para las series:
![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/9f30760a-38ba-4582-8353-4f03ee13725e)

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/398cffd4-bc76-43d8-b7bc-51033595c8ed)

Tras probarlo de esta manera, también existe la opción de integrarlo como fichero .jar en otro proyecto totalmente nuevo. 
¡IMPORTANTE! Los ficheros de audio deben ser correctamente almacenados en el directorio "src/main/resources":

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/5b2d6aa1-e9cb-4185-866e-af191af6254a)

Tras crear el fichero .jar ayudandonos en Maven con los siguientes comandos o ejecutando "mvn clean package":

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/fff3a8fb-0e98-4604-b3c9-4d43b5f5542a)

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/3eb16514-10d8-423f-8a1d-bd2fd3602a24)

Creamos un nuevo proyecto, por ejemplo, "PruebaTemporizador", y almacenamos el fichero .jar que se creó en el primer proyecto en la carpeta "target" en otra carpeta del nuevo proyecto llamada por convenio "lib":

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/aaf1baef-d03b-489f-a81f-0cceb0120e33)

Y guardamos los ficheros de audio en "src/main/resources", en el main del nuevo proyecto podemos copiar el mismo código del método main que puede verse arriba en este documento.

Teniendo esto lo único que debemos hacer es ejecutar estos 2 comandos desde una terminal en el directorio raíz del proyecto de prueba:

javac -cp lib/ComponenteTimer-1.0.jar -d target src/main/java/com/mycompany/pruebatemporizador/PruebaTemporizador.java

Y luego:

java -cp lib/ComponenteTimer-1.0.jar;target com.mycompany.pruebatemporizador.PruebaTemporizador

Veremos la misma pequeá interfaz gráfica que para probar el componente por separado:

![image](https://github.com/jvidalhern/ComponenteTimer/assets/150078940/3f4ccb54-ef7a-4dae-9d44-96772a3a9245)














