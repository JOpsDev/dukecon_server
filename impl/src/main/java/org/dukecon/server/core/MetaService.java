package org.dukecon.server.core;

import org.dukecon.server.repositories.ConferenceDataProvider;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Component
@Path("/meta")
// @Api(value = "/meta", description = "Conference meta data endpoint")
public class MetaService {
    List<ConferenceDataProvider> talkProviders;

    @Inject
    MetaService(List<ConferenceDataProvider> talkProviders) {
        this.talkProviders = talkProviders;
    }

    @GET
    @Path("{id}")
//     @ApiOperation(value = "Get conference meta data of given conference")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeta(@PathParam("id") String id) {
        Optional<ConferenceDataProvider> provider = talkProviders.stream().filter(p -> p.getConference().getId().equals(id)).findFirst();
        if (provider.isPresent()) {
            return Response.ok().entity(provider.get().getConference().getMetaData()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Deprecated
    @GET
//     @ApiOperation(value = "Get conference meta data of JavaLand 2016 conference (Deprecated)")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeta() {
        String id = "499959"; //hardcoded to Javaland 2016
        return getMeta(id);
    }

    @GET
//     @ApiOperation(value = "Check if service is up")
    @Path("ping")
    public Response ping() {
        Map<String, String> m = new HashMap<>(1);
        m.put("last_updated", "2015-05-19T16:20:11");
        return Response.ok().entity(m).build();
    }
}
