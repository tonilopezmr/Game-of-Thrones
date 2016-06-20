package es.npatarino.android.gotchallenge.data.source.remote;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class JsonMapperTest {

    public static final String TYRION_LANNISTER = "Tyrion Lannister";
    public static final String HOUSE_ID = "50fab25b";
    private JsonMapper jsonMapper = new JsonMapper(new Gson());


    private static final String JSON_RESPONSE = "[\n" +
            "  {\n" +
            "    \"name\": \"Tyrion Lannister\",\n" +
            "    \"imageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/8462e9de-a1f6-4333-a83b-2cf1f1521827.jpg\",\n" +
            "\t\"description\": \"In A Game of Thrones (1996), Tyrion is introduced as the third and youngest child of wealthy and powerful Tywin Lannister, the former Hand of the King. Tyrion's elder sister Cersei is the Queen of Westeros by virtue of her marriage to King Robert Baratheon, and Cersei's male twin Jaime is one of the Kingsguard, the royal security detail. Described as an ugly ('for all the world like a gargoyle'), malformed dwarf with mismatched green and black eyes, Tyrion possesses the pale blond hair of a Lannister but has a complicated relationship with the rest of them.\",\n" +
            "    \"houseImageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/lannister.jpg\",\n" +
            "    \"houseId\": \"50fab25b\",\n" +
            "    \"houseName\": \"House Lannister\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Daenerys Targaryen\",\n" +
            "    \"imageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/c8533a36-5e15-4a57-baed-fc87f7eba578.jpg\",\n" +
            "\t\"description\": \"Daenerys Targaryen is the daughter of King Aerys II Targaryen (also referred to as The Mad King) and his sister-wife Rhaella, and is one of the last survivors of House Targaryen. She serves as the third-person narrator of thirty-one chapters throughout A Game of Thrones, A Clash of Kings, A Storm of Swords, and A Dance with Dragons. She is the only monarch or claimant of such given point of view chapters in the novels. Thirteen years before the events of the series, she was born in the midst of a storm, earning her the nickname 'Stormborn'. Shortly thereafter, Daenerys and her brother Viserys fled to Braavos; Rhaella had died in childbirth. They spent the following years wandering the Free Cities.\",\n" +
            "    \"houseImageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/targaryen.jpg\",\n" +
            "    \"houseId\": \"78faa61d\",\n" +
            "    \"houseName\": \"House Targaryen\"\n" +
            "  }, {\n" +
            "    \"name\": \"Stannis Baratheon\",\n" +
            "    \"imageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/ab65609e-7b73-40b7-bb58-dbc4cc9d308a.jpg\",\n" +
            "\t\"description\": \"Stannis Baratheon is the younger brother of King Robert and older brother of Renly. He is portrayed as a brooding and humorless man with a harsh sense of justice and an obsession with slights both real and imagined. He is regarded as a skilled but overcautious military commander.\",\n" +
            "    \"houseImageUrl\": \"https://s3-eu-west-1.amazonaws.com/npatarino/got/baratheon.jpg\",\n" +
            "    \"houseId\": \"8b7f8fd5\",\n" +
            "    \"houseName\": \"House Baratheon\"\n" +
            "  }]";

    @Test
    public void
    map_characters() {
        List<GoTCharacter> characterList = jsonMapper.transformList(JSON_RESPONSE);


        assertThat(characterList.size(), is(3));

        GoTCharacter character = characterList.get(0);

        assertThat(character.getName(), is(TYRION_LANNISTER));
        assertThat(character.getHouseId(), is(HOUSE_ID));
        assertNotNull(character.getDescription());
        assertNotNull(character.getImageUrl());

    }
}