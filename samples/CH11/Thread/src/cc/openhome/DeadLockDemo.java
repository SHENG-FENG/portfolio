package cc.openhome;

class Resource {
    private String name;
    private int resource;

    Resource(String name, int resource) {
        this.name = name;
        this.resource = resource;
    }

    String getName() {
        return name;
    }

    synchronized int doSome() {
        return ++resource;
    }

    synchronized void cooperate(Resource resource) {
        resource.doSome();
        System.out.printf("%s 整合 %s 的資源%n",
                this.name, resource.getName());
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        var resource1 = new Resource("resource1", 10);
        var resource2 = new Resource("resource2", 20);
        
        var thread1 = new Thread(() -> {
            for(var i = 0; i < 10; i++) {
                resource1.cooperate(resource2);
            }
        });
        var thread2 = new Thread(() -> {
            for(var i = 0; i < 10; i++) {
                resource2.cooperate(resource1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
