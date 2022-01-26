package com.tayag.helpers;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // * * * * *
    // Addition
    // * * * * *

    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2D add(Vector2D other) {
        this.x = this.x + other.x;
        this.y = this.y + other.y;

        return this;
    }

    // * * * * * *
    // Subtraction
    // * * * * * *

    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2D subtract(Vector2D other) {
        this.x = this.x - other.x;
        this.y = this.y - other.y;

        return this;
    }

    // * * * * * * * *
    // Multiplication
    // * * * * * * * *

    public Vector2D multiply(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;

        return this;
    }
}
