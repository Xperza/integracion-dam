# Anteproyecto

## OBJETIVOS

Crear una aplicación de gestión de tareas. Estará principalmente enfocada hacia empresas, contará con un gestor de tareas, donde podrás almacenar las tareas de cada proyecto y asignarselas a cada persona con un nivel de prioridad.

## ANÁLISIS DEL SOFTWARE

Estos serán los componentes con los que contará la aplicación:

- Una base de datos que almacenará tanto usuarios con sus datos como los proyectos y las tareas. 
- Ver la lista de proyectos (en los que estás) y dentro de estos todas las tareas a realizar.
- Las tareas tendrán un título, descripción, un nivel de prioridad y las personas que estén inscritas a esta.
- Recibir un correo ya sea por email o una notificación por la propia aplicación del cambio del estado de tu tarea.

![image](https://github.com/Xperza/integracion-dam/assets/90802641/fd565d15-5d8c-4998-b7e4-ece9d4027073)

## DISEÑO DEL SOFTWARE

El diseño del software será el siguiente implementando el uso de las aplicaciones y frameworks de la empresa utilizaré:

- Back-end Java con el framework Spring, donode se llevará a cabo la conexión con JPA a SQL para pedir las consultas necesarias a traves de endpoints.
- Front-end para la interfaz principal de la aplicación donde utilizaré Angular que utiliza HTML, CSS y TypeScript, esta se conectará con el back mediante los endpoints para pedir las consultas.
- Gestión de las bases de datos con SQL.
- El envío de mensajes de correo se hará con Spring.

## ESTIMACIÓN DE COSTES

Principalmente no tendrá ningún coste aparte del tiempo empleado en la creación de la aplicación, tendrá un trabajo total de 2 meses y las aplicaciones utilizadas son todas gratuitas.
