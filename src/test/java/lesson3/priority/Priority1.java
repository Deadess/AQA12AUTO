package lesson3.priority;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Priority1 {
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
}