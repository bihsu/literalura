<h1 align="center">Desafío Alura - Oracle Nex - LITERALURA</h1>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  ![Eclipse](https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white)


## Descripción del proyecto

Este proyecto nos permite consultar los datos de la api de [Gutendex](https://gutendex.com/) y buscar libros por coincidencias de la palabra ingresada con el título de los libros, esta información es guardad en una base de datos para luego ser consultada por el usuario mediante un menú por consola.

## :hammer:Opciones del Menú

- `1 - Buscar por título`: Permite listar los libros que contengan la palabra escrita en su título, luego de eso escogeremos el libro de nuestro gusto por el ID
- `2- Listar libros registrados`: Lista todos los libros guardados en la base de datos
- `3 - Listar autores registrados`: Lista los autores registrado en la base de datos
- `4 - Listar autores vivos en un determinado año`: Lista todos los autores que estén vivos en un determiando año
- `5 - Listar libros por idioma`: Muestra un menú de 4 idiomas del cual se debe selecciona runo para que se liste los libros en ese idioma
- `Salir`: Sale del programa

## Herramientas utilizadas
### - Java 21
### - Eclipse IDE

## Cómo se implementó la base de datos
- Se realizó una relación de muchos a muchos entre las entidades Book y Persona según la documentación de [Gutendex](https://gutendex.com/).
- Al momento de hacer la busqueda por título el API de [Gutendex](https://gutendex.com/) muestra varios libros que tengan el título con una coincidencia, se muestran incluso varaias veces el mismo libro, se pusieron las restricciones que aunque tengan datos distintos no se peudan duplicar los títulos.
- Se implementó una validación para ver si el autor del libro a registrar ya estaba en la base de datos.

## Autores del proyecto
| [<img src="https://avatars.githubusercontent.com/u/32920648?s=400&v=4" width=115><br><sub>Adalidt Ancachi Limachi</sub>](https://github.com/bihsu) |
| :---: |

### Entrenamiento Backend con el apoyo de 
- [Alura Latam](https://www.aluracursos.com/)
- [Oracle Nex Education](https://www.oracle.com/pe/education/oracle-next-education/)
