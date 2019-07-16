package spring.task.service;

import java.util.concurrent.Future;

public interface AsyncService {

    void asyncTask();

    Future<String> asyncWithReture(String name);
}
