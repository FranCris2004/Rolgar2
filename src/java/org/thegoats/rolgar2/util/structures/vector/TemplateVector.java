package org.thegoats.rolgar2.util.structures.vector;

import org.thegoats.rolgar2.util.Assert;

public class TemplateVector<T> {
        // ATRIBUTOS -------------------------------------------------------------
        private T[] data = null;
        private T firstData;

        // CONSTRUCTOR -----------------------------------------------------------
        /**
         * pre:
         *   - longitud: entero >= 1 (cantidad de elementos)
         *   - datoInicial: valor con el que se inicializan todas las posiciones
         * throws Exception si la longitud es inválida
         * post:
         *   - crea un vector de 'longitud' y lo deja todo en 'datoInicial'
         */
        public TemplateVector(int length, T firstData) throws Exception {
            if (length < 1) {
                throw new Exception("La longitud debe ser mayor o igual a 1");
            }
            this.data = makeVector(length);
            this.firstData = firstData;
            for (int i = 0; i < this.getLength(); i++) {
                this.data[i] = firstData;
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
        public void add(int position, T data) throws Exception {
            Assert.inRange(position, 1, this.getLength(), "posicion debe estar entre 1 y el largo del vector");
            this.data[position - 1] = data;
        }

        /**
         * pre:
         *   - posicion: valor entre 1 y el largo del vector
         * return:
         *   - devuelve el valor en esa posicion
         * throws Exception si la posicion no está en rango
         */
        public T get(int position) throws Exception {
            Assert.inRange(position, 1, this.getLength(), "posicion debe estar entre 1 y el largo del vector");
            return this.data[position - 1];
        }

        /**
         * pre:
         *   - posicion: valor entre 1 y el largo del vector
         * throws Exception si la posicion no está en rango
         * post:
         *   - “remueve” dejando el valor inicial en esa posición
         */
        public void remove(int position) throws Exception {
            Assert.inRange(position, 1, this.getLength(), "posicion debe estar entre 1 y el largo del vector");
            this.data[position - 1] = this.firstData;
        }

        /**
         * Agrega en la siguiente posición “vacía” (la que tiene datoInicial).
         * Si no hay lugares vacíos, duplica la capacidad y agrega al principio
         * del tramo nuevo.
         *
         * return:
         *   - la posición (1..N) en la que se guardó el dato
         */
        public int add(T data) throws Exception {
            // Buscar primer hueco con datoInicial
            for (int i = 0; i < this.getLength(); i++) {
                if (this.data[i] == this.firstData) {   // ojo: compara por referencia
                    this.data[i] = data;
                    return i + 1;
                }
            }

            // No había hueco: duplicar capacidad
            T[] temp = makeVector(this.getLength() * 2);
            for (int i = 0; i < this.getLength(); i++) {
                temp[i] = this.data[i];
            }

            int position = this.getLength(); // índice 0-based donde comienza el tramo nuevo
            this.data = temp;
            this.data[position] = data;

            // Rellenar el resto del tramo nuevo con datoInicial
            for (int i = position + 1; i < this.getLength(); i++) {
                this.data[i] = this.firstData;
            }

            return position + 1; // devolver en base 1
        }

        // HELPERS PRIVADOS ------------------------------------------------------
        /**
         * Crea un arreglo genérico T[] de la longitud solicitada.
         */
        @SuppressWarnings("unchecked")
        private T[] makeVector(int length) throws Exception {
            if (length < 1) {
                throw new Exception("La longitud debe ser mayor o igual a 1");
            }
            return (T[]) new Object[length];
        }

        // GETTERS ---------------------------------------------------------------
        public int getLength() {
            return this.data.length;
        }
    }


