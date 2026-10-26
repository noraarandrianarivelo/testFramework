package test.Controller;

import mg.itu.annotation.*;

@Controller
public class Test2 {
    @UrlMapping(value="/haha", method="GET")
    public String go(){
        return "GET /haha";
    }
}
