package org.dukecon.server.formes2dukecon

import com.fasterxml.jackson.databind.ObjectMapper
import org.dukecon.model.Conference
import org.dukecon.server.conference.ConferencesConfiguration
import org.dukecon.server.repositories.RawDataMapper
import org.dukecon.server.repositories.RawDataResources
import org.dukecon.server.repositories.doag.DoagDataExtractor
import org.dukecon.server.repositories.doag.DoagJsonMapper

import java.time.LocalDate

class FormesToDukecon {
    public static void main(String[] args) {
        ConferencesConfiguration.Conference conferenceConfig = new ConferencesConfiguration.Conference (
                id: "javaland2017",
                name: "Javaland",
                year: "2017",
                url: "file:...",
                homeUrl: "file:...",
                startDate: LocalDate.now()
        )
        RawDataResources rawDataResources = new RawDataResources([eventsData: 'https://www.javaland.eu/api/schedule/programm/jl.php?key=TestJL&id=527880'])
        RawDataMapper rawDataMapper = new DoagJsonMapper(rawDataResources)
        rawDataMapper.initMapper()
        DoagDataExtractor doagDataExtractor = new DoagDataExtractor(conferenceConfig, rawDataMapper, new DummySpeakerImageService())
        Conference conference = doagDataExtractor.conference
        ObjectMapper objectMapper = new ObjectMapper()
        File json = new File("conference.json")
        objectMapper.writeValue(json, conference);
        println json.absolutePath

    }
}
