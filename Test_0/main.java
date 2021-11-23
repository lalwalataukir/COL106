//I had written the code in previous recording without camera.
class Branch {
//Removing static after seeing JavaLabModule
    public void getBranch() {
        System.out.println("I am in IIT");
    }
}

class ComputerScience extends Branch {
//Removing static after seeing JavaLabModule
    public void getBranch() {
        System.out.println("I am in Computer Science");
    }
}

class Electrical extends Branch {
//Removing static after seeing JavaLabModule
    public void getBranch() {
        System.out.println("I am in Electrical");
    }
}

public class main {
    public static void main(String args[]) {
        Branch a = new Branch();
        Branch b = new ComputerScience();
        Branch c = new Electrical();
        a.getBranch();
        b.getBranch();
        c.getBranch();
    }
}