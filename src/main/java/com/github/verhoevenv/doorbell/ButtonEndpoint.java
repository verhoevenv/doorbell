package com.github.verhoevenv.doorbell;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/button")
public class ButtonEndpoint {

    @POST
    @Path("/ring")
    public void ring() {
        System.out.println("Ding-dong");
    }
}
