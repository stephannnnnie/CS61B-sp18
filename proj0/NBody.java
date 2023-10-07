public class NBody {
    public static double readRadius(String path){
        In in = new In(path);
        int N = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String path){
        In in = new In(path);

        int N = in.readInt();
        Planet[] planets = new Planet[N];
        if (in==null){
            return planets;
        }
        double radius = in.readDouble();

        for (int p = 0; p < N; p++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[p]=new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-r,r);
        StdDraw.setYscale(-r,r);
        StdDraw.clear();
        StdDraw.picture(0,0,"images/starfield.jpg");
        for (double ts = 0; ts < T; ) {
            Double[] xForces = new Double[planets.length];
            Double[] yForces = new Double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(ts, xForces[i], yForces[i]);
                planets[i].draw();
                StdDraw.show();
                StdDraw.pause(10);
            }
            ts = ts+dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}