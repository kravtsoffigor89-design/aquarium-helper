package com.aquarium.model;

public class Fish {
    private String name;
    private double requiredLiters; // скільки літрів потрібно одній рибці
    private String Type;          // мирна чи хижа

    public Fish(String name, double requiredLiters, String type) {
        this.name = name;
        this.requiredLiters = requiredLiters;
        this.Type = type;
    }

    public String getName() { return name; }
    public double getRequiredLiters() { return requiredLiters; }
    public String getType() { return Type; }

    @Override
    public String toString() { return name; }
}