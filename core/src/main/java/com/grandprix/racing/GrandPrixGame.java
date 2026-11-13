
package com.grandprix.racing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class GrandPrixGame extends ApplicationAdapter {

    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Environment environment;
    private Array<ModelInstance> scene;
    private Array<Model> models;

    @Override
    public void create() {

        modelBatch = new ModelBatch();
        scene = new Array<>();
        models = new Array<>();

        camera = new PerspectiveCamera(
            67,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        camera.position.set(0, 2.0f, 4);
        camera.lookAt(0, 2.0f, -30);
        camera.near = 0.1f;
        camera.far = 250f;
        camera.update();

        environment = new Environment();

        environment.set(
            new ColorAttribute(
                ColorAttribute.AmbientLight,
                0.8f, 0.8f, 0.8f, 1f
            )
        );

        environment.add(
            new DirectionalLight().set(
                1f, 1f, 1f,
                -1f, -0.8f, -0.2f
            )
        );

        createTrack();
    }

    private void addBox(
        float width,
        float height,
        float depth,
        float x,
        float y,
        float z,
        Color color
    ) {

        ModelBuilder builder = new ModelBuilder();

        Model model = builder.createBox(
            width,
            height,
            depth,
            new Material(
                ColorAttribute.createDiffuse(color)
            ),
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal
        );

        models.add(model);

        ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(x, y, z);

        scene.add(instance);
    }

    private void createTrack() {

        // Asfalto
        addBox(
            14, 0.2f, 120,
            0, -0.1f, -50,
            Color.DARK_GRAY
        );

        // Grama
        addBox(
            200, 0.1f, 150,
            0, -0.3f, -50,
            Color.FOREST
        );

        // Faixas laterais
        addBox(
            0.25f, 0.03f, 120,
            -6.5f, 0.03f, -50,
            Color.WHITE
        );

        addBox(
            0.25f, 0.03f, 120,
            6.5f, 0.03f, -50,
            Color.WHITE
        );

        // Barreiras
        addBox(
            0.5f, 1.0f, 120,
            -8, 0.5f, -50,
            Color.RED
        );

        addBox(
            0.5f, 1.0f, 120,
            8, 0.5f, -50,
            Color.RED
        );

        // Painel do cockpit
        addBox(
            3.5f, 0.35f, 0.8f,
            0, 0.65f, 1.5f,
            Color.BLACK
        );
    }

    @Override
    public void render() {

        Gdx.gl.glViewport(
            0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        Gdx.gl.glClearColor(
            0.35f, 0.65f, 0.95f, 1f
        );

        Gdx.gl.glClear(
            GL20.GL_COLOR_BUFFER_BIT |
            GL20.GL_DEPTH_BUFFER_BIT
        );

        camera.update();

        modelBatch.begin(camera);
        modelBatch.render(scene, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {

        modelBatch.dispose();

        for (Model model : models) {
            model.dispose();
        }
    }
}
