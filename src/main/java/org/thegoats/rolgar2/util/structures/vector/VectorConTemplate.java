package org.thegoats.rolgar2.util.structures.vector;

public class VectorConTemplate<T> {
        // ATRIBUTOS -------------------------------------------------------------
        private T[] datos = null;
        private T datoInicial;

        // CONSTRUCTOR -----------------------------------------------------------
        /**
         * pre:
         *   - longitud: entero >= 1 (cantidad de elementos)
         *   - datoInicial: valor con el que se inicializan todas las posiciones
         * throws Exception si la longitud es inválida
         * post:
         *   - crea un vector de 'longitud' y lo deja todo en 'datoInicial'
         */
        public VectorConTemplate(int longitud, T datoInicial) throws Exception {
            if (longitud < 1) {
                throw new Exception("La longitud debe ser mayor o igual a 1");
            }
            this.datos = crearVector(longitud);
            this.datoInicial = datoInicial;
            for (int i = 0; i < this.getLongitud(); i++) {
                this.datos[i] = datoInicial;
            }
        }

        // METODOS DE COMPORTAMIENTO --------------------------------------------

        /**
         * pre:
         *   - posicion: valor entre 1 y el largo del vector
         *   - dato: valor a guardar
         * throws Exception si la posicion no está en rango
         * post:
         *   - guarda el dato en la posicion dada
         */
        public void agregar(int posicion, T dato) throws Exception {
            validarPosicion(posicion);
            this.datos[posicion - 1] = dato;
        }

        /**
         * pre:
         *   - posicion: valor entre 1 y el largo del vector
         * return:
         *   - devuelve el valor en esa posicion
         * throws Exception si la posicion no está en rango
         */
        public T obtener(int posicion) throws Exception {
            validarPosicion(posicion);
            return this.datos[posicion - 1];
        }

        /**
         * pre:
         *   - posicion: valor entre 1 y el largo del vector
         * throws Exception si la posicion no está en rango
         * post:
         *   - “remueve” dejando el valor inicial en esa posición
         */
        public void remover(int posicion) throws Exception {
            validarPosicion(posicion);
            this.datos[posicion - 1] = this.datoInicial;
        }

        /**
         * Agrega en la siguiente posición “vacía” (la que tiene datoInicial).
         * Si no hay lugares vacíos, duplica la capacidad y agrega al principio
         * del tramo nuevo.
         *
         * return:
         *   - la posición (1..N) en la que se guardó el dato
         */
        public int agregar(T dato) throws Exception {
            // Buscar primer hueco con datoInicial
            for (int i = 0; i < this.getLongitud(); i++) {
                if (this.datos[i] == this.datoInicial) {   // ojo: compara por referencia
                    this.datos[i] = dato;
                    return i + 1;
                }
            }

            // No había hueco: duplicar capacidad
            T[] temp = crearVector(this.getLongitud() * 2);
            for (int i = 0; i < this.getLongitud(); i++) {
                temp[i] = this.datos[i];
            }

            int posicion = this.getLongitud(); // índice 0-based donde comienza el tramo nuevo
            this.datos = temp;
            this.datos[posicion] = dato;

            // Rellenar el resto del tramo nuevo con datoInicial
            for (int i = posicion + 1; i < this.getLongitud(); i++) {
                this.datos[i] = this.datoInicial;
            }

            return posicion + 1; // devolver en base 1
        }

        // HELPERS PRIVADOS ------------------------------------------------------

        /**
         * pre:
         *   - posicion entre 1 y getLongitud()
         * throws Exception si está fuera de rango
         */
        private void validarPosicion(int posicion) throws Exception {
            if (posicion < 1 || posicion > this.getLongitud()) {
                throw new Exception("La posición " + posicion + " no está en el rango 1.." + this.getLongitud());
            }
        }

        /**
         * Crea un arreglo genérico T[] de la longitud solicitada.
         */
        @SuppressWarnings("unchecked")
        private T[] crearVector(int longitud) throws Exception {
            if (longitud < 1) {
                throw new Exception("La longitud debe ser mayor o igual a 1");
            }
            return (T[]) new Object[longitud];
        }

        // GETTERS ---------------------------------------------------------------
        public int getLongitud() {
            return this.datos.length;
        }
    }


