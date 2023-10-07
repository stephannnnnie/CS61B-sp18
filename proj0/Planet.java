
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G  = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV,double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    public Planet(Planet p){
        this.xxPos=p.xxPos;
        this.yyVel=p.yyVel;
        this.xxVel=p.xxVel;
        this.yyPos=p.yyPos;
        this.mass=p.mass;
        this.imgFileName=p.imgFileName;
    }

    public double calcDistance(Planet p){
        double r,dx,dy;
        dx = this.xxPos-p.xxPos;
        dy = this.yyPos-p.yyPos;
        r = Math.sqrt(dx*dx+dy*dy);
        return r;
    }

    public double calcForceExertedBy(Planet p){
        double r = calcDistance(p);
        double f = G*p.mass*mass/(r*r);
        return f;
    }

    public double calcForceExertedByX(Planet p){
        double fx = calcForceExertedBy(p)*(p.xxPos-xxPos)/calcDistance(p);
        return fx;
    }
    public double calcForceExertedByY(Planet p){
        double fy = calcForceExertedBy(p)*(p.yyPos-yyPos)/calcDistance(p);
        return fy;
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double net = 0;
        for (int i = 0; i < planets.length; i++) {
            if (!this.equals(planets[i])) {
                net += calcForceExertedByX(planets[i]);
            }
        }
        return net;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double net = 0;
        for (int i = 0; i < planets.length; i++) {
            if (!this.equals(planets[i])){
                net+=calcForceExertedByY(planets[i]);
            }
        }
        return net;
    }

    public void update(double dt, double fx, double fy){
        double ax = fx/mass;
        double ay = fy/mass;
        xxVel = xxVel+dt*ax;
        yyVel = yyVel+dt*ay;
        xxPos += dt*xxVel;
        yyPos += dt*yyVel;
    }

    public void draw(){
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }
}