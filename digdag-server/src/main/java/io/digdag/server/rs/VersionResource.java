package io.digdag.server.rs;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import io.digdag.client.Version;
import io.digdag.client.api.RestVersionCheckResult;
import io.digdag.server.ClientVersionChecker;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@Produces("application/json")
public class VersionResource
{
    // GET  /api/version                                     # returns version of this server
    // GET  /api/version/check?client=                       # checks given version and returns its result

    private final Version version;
    private final ClientVersionChecker clientVersionChecker;

    @Inject
    public VersionResource(Version version, ClientVersionChecker clientVersionChecker)
    {
        this.version = version;
        this.clientVersionChecker = clientVersionChecker;
    }

    @GET
    @Path("/api/version")
    public Map<String, Object> getVersion()
    {
        return ImmutableMap.of("version", version.toString());
    }

    @GET
    @Path("/api/version/check")
    public RestVersionCheckResult checkClientVersion(@QueryParam("client") String clientVersionString)
    {
        Version clientVersion = Version.parse(clientVersionString);
        return RestVersionCheckResult.builder()
            .serverVersion(version.toString())
            .upgradeRecommended(clientVersionChecker.isUpgradeRecommended(clientVersion))
            .apiCompatible(clientVersionChecker.isApiCompatible(clientVersion))
            .build();
    }
}
