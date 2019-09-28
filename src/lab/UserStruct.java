package lab;

public class UserStruct implements java.io.Serializable{
    protected String login;
    protected String password;
    protected boolean isAdmin;
    protected boolean isBlocked;
    protected boolean hasRestrictions;
    protected boolean isFirst;

    public void setUser(String u, String p,boolean iA,boolean iB,boolean hR,boolean iF){
        login = u;
        password = p;
        isAdmin = iA;
        isBlocked = iB;
        hasRestrictions = hR;
        isFirst = iF;
    }
    public void print(){
        System.out.println("-----UserStruct-----");
        System.out.println(login);
        System.out.println(password);
        System.out.println(isAdmin);
        System.out.println(isBlocked);
        System.out.println(hasRestrictions);
        System.out.println(isFirst);
    }
}
