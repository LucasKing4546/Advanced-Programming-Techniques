package domain;

import java.util.ArrayList;

public class Entity {
    private String coefficients;
    private int degree;
    private String difficulty;

    public Entity(String coefficients, int degree, String difficulty) {
        this.coefficients = coefficients;
        this.degree = degree;
        this.difficulty = difficulty;
    }

    public String getCoefficients() {
        return coefficients;
    }

    public int getDegree() {
        return degree;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setCoefficients(String coefficients) {
        this.coefficients = coefficients;
    }

    public String getMathematicalForm(){
        String[] list = coefficients.split(",");
        StringBuilder sb = new StringBuilder();
        if (degree == 1){
            sb.append(list[0]).append(" * x");
            if (list[1].contains("-")){
                sb.append(list[1]);
            }
            else{
                sb.append(" + ").append(list[1]);
            }
        }
        else{
            sb.append(list[0]).append(" * x^2");
            if (list[1].contains("-")){
                sb.append(list[1]).append(" * x");
            }
            else{
                sb.append(" + ").append(list[1]);
            }
            if (list[2].contains("-")){
                sb.append(list[2]);
            }
            else{
                sb.append(" + ").append(list[2]);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "coefficients='" + coefficients + '\'' +
                ", degree=" + degree +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
