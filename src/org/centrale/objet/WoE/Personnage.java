package org.centrale.objet.WoE;

public class Personnage extends Creature {

    private String nom;
    private int ptVie;
    private int degAtt;
    private int ptPar;
    private int pageAtt;
    private int pagePar;
    private int distAttMax;

    private Point2D pos;

    public Personnage(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax,
            Point2D pos) {
        this.nom = nom;
        this.ptVie = ptVie;
        this.degAtt = degAtt;
        this.ptPar = ptPar;
        this.pageAtt = pageAtt;
        this.pagePar = pagePar;
        this.distAttMax = distAttMax;
        this.pos = new Point2D(pos);
    }

    public Personnage(Personnage p) {
        this.nom = p.nom;
        this.ptVie = p.ptVie;
        this.degAtt = p.degAtt;
        this.ptPar = p.ptPar;
        this.pageAtt = p.pageAtt;
        this.pagePar = p.pagePar;
        this.distAttMax = p.distAttMax;
        this.pos = new Point2D(p.pos);
    }

    public Personnage() {
        this.nom = "Personnage";
        this.ptVie = 100;
        this.degAtt = 10;
        this.ptPar = 5;
        this.pageAtt = 5;
        this.pagePar = 5;
        this.distAttMax = 1;
        this.pos = new Point2D();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPtVie() {
        return ptVie;
    }

    public void setPtVie(int ptVie) {
        this.ptVie = ptVie;
    }

    public int getDegAtt() {
        return degAtt;
    }

    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }

    public int getPtPar() {
        return ptPar;
    }

    public void setPtPar(int ptPar) {
        this.ptPar = ptPar;
    }

    public int getPageAtt() {
        return pageAtt;
    }

    public void setPageAtt(int pageAtt) {
        this.pageAtt = pageAtt;
    }

    public int getPagePar() {
        return pagePar;
    }

    public void setPagePar(int pagePar) {
        this.pagePar = pagePar;
    }

    public int getDistAttMax() {
        return distAttMax;
    }

    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public void deplacer(int dx, int dy) {
        this.pos.translate(dx, dy);
    }

    public void afficher() {
        System.out.println("Personnage: nom=" + nom + ", ptVie=" + ptVie + ", degAtt=" + degAtt +
                ", ptPar=" + ptPar + ", pageAtt=" + pageAtt + ", pagePar=" + pagePar +
                ", distAttMax=" + distAttMax);
        pos.afficher();
    }

}
