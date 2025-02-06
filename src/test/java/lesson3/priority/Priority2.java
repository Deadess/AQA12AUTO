package lesson3.priority;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Priority2.CustomRunner.class)
public class Priority2 {

    @Test
    public void g() { assertTrue(true); }
    @Test
    public void f() { assertTrue(true); }
    @Test
    public void e() { assertTrue(true); }
    @Test
    public void d() { assertTrue(true); }
    @Test
    public void c() { assertTrue(true); }
    @Test
    public void b() { assertTrue(true); }
    @Test
    public void a() { assertTrue(true); }
    public static class CustomRunner extends Runner {

        private final Class<?> testClass;
        public CustomRunner(Class<?> testClass) {
            this.testClass = testClass;
        }
        @Override
        public Description getDescription() {
            return Description.createTestDescription(testClass, "Custom runner for tests in reverse-alphabetical order");
        }
        @Override
        public void run(RunNotifier notifier) {
            List<Description> testMethods = getDescription().getChildren();
            Collections.reverse(testMethods);
            for (Description method : testMethods) {
                runLeaf(method, notifier);
            }
        }
        private void runLeaf(Description description, RunNotifier notifier) {
            try {
                notifier.fireTestStarted(description);
                testClass.getMethod(description.getMethodName()).invoke(testClass.newInstance());
                notifier.fireTestFinished(description);
            } catch (Exception e) {
                notifier.fireTestFailure(new org.junit.runner.notification.Failure(description, e));
            }
        }
    }
}