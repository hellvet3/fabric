package net.fabricmc.fabric.api.datagen.v1;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Acceptable class for {@link BlockStateModelGenerator}'s blockStateCollector.
 * makes generating more specific block models easier for modders.
 */

public class BlockModelSupplier implements Supplier<JsonElement> {
    protected final JsonObject jsonObject;

    public BlockModelSupplier(String type)
    {
        this.jsonObject = new JsonObject();
		this.jsonObject.addProperty("parent", "minecraft:block/" + type);
    }

	/**
 	 * Have an acceptable <modID> parameter in case of custom model parents.
	 */
	public BlockModelSupplier(String modID, String type)
    {
        this.jsonObject = new JsonObject();
		this.jsonObject.addProperty("parent", modID + ":block/" + type);
    }

	/**
	 * Add HashMap textures to the model's JsonObject.
	 *
	 * @param textureMap HashMap containing the data for the textures.
	 */
    public BlockModelSupplier addTextureData(HashMap<String, Identifier> textureMap)
    {
        JsonObject textureData = new JsonObject();
        for (String key : textureMap.keySet()) {
            Identifier identifier = textureMap.get(key);
            textureData.addProperty(key, identifier.getNamespace() + ":" + identifier.getPath());
        }

        this.jsonObject.add("textures", textureData);
        return this;
    }

	/**
	 * Add a sample texture as 'all' of the textures. Can only be used on the cube_all parent.
	 *
	 * @param texture {@link Identifier} for the location of the texture.
	 */
    public BlockModelSupplier simpleCubeAllTextures(Identifier texture)
    {
        JsonObject textureData = new JsonObject();
        textureData.addProperty("all", texture.getNamespace() + ":" + texture.getPath());

        this.jsonObject.add("textures", textureData);
        return this;
    }

	/**
	 * Returns the {@link JsonObject} for the Model.
     *
  	 * @return the supplier's JsonObject
	 */
    @Override
    public JsonElement get() {
        return this.jsonObject;
    }
}
