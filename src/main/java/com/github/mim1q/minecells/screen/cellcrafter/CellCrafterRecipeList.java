package com.github.mim1q.minecells.screen.cellcrafter;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.recipe.CellForgeRecipe;
import com.github.mim1q.minecells.screen.cellcrafter.CellCrafterScreen.IngredientDisplay;
import com.github.mim1q.minecells.screen.cellcrafter.CellCrafterScreen.TexturedButton;
import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.github.mim1q.minecells.client.gui.toast.SurfaceUtil.backgroundTexture;

public class CellCrafterRecipeList {
  public static final Identifier RECIPES_SCREEN_TEXTURE = MineCells.createId("textures/gui/cell_crafter/recipes.png");

  private final CellCrafterScreen parent;

  private final List<DisplayedRecipe> allRecipes = new ArrayList<>();
  private final List<DisplayedRecipe> currentRecipes = new ArrayList<>();
  private final List<DisplayedRecipe> visibleRecipes = new ArrayList<>();
  private DisplayedRecipe selectedRecipe = null;
  private CellForgeRecipe.Category selectedCategory = CellForgeRecipe.Category.GEAR;

  private final GridLayout grid;
  private final TexturedButton downButton;
  private final TexturedButton upButton;
  private final TexturedButton applyButton;
  private final LabelComponent label;
  private final TextBoxComponent textBox;
  private FlowLayout container;
  private IngredientDisplay ingredientDisplay = null;

  private String search = "";
  private int scrollOffset = 0;

  public CellCrafterRecipeList(CellCrafterScreen parent) {
    this.parent = parent;
    grid = Containers.grid(Sizing.content(), Sizing.content(), 5, 6);

    upButton = new TexturedButton(
      it -> scrollUp(),
      RECIPES_SCREEN_TEXTURE,
      208, 48
    );
    upButton.tooltip(Text.translatable("block.minecells.cell_crafter.scroll_up"));

    downButton = new TexturedButton(
      it -> scrollDown(),
      RECIPES_SCREEN_TEXTURE,
      224, 48
    );
    downButton.tooltip(Text.translatable("block.minecells.cell_crafter.scroll_down"));

    applyButton = new TexturedButton(
      it -> {
        this.parent.setSelectedRecipe(selectedRecipe.recipe());
        this.parent.toggleRecipeList();
      },
      RECIPES_SCREEN_TEXTURE,
      224, 0
    );

    label = Components.label(selectedCategory.getName());
    label.shadow(false)
      .color(Color.BLACK)
      .positioning(Positioning.absolute(44, 4));

    textBox = new NoShadowTextBox(Sizing.fixed(70));
  }

  public void build(FlowLayout rootComponent) {
    container = Containers.verticalFlow(Sizing.fixed(200), Sizing.fixed(176));
    container
      .surface(backgroundTexture(RECIPES_SCREEN_TEXTURE, 160, 176))
      .horizontalAlignment(HorizontalAlignment.LEFT)
      .verticalAlignment(VerticalAlignment.TOP)
      .padding(Insets.of(8))
      .allowOverflow(true);

    textBox.setDrawsBackground(false);
    textBox.setRenderTextProvider((string, firstCharacterIndex) ->
      OrderedText.styledForwardsVisitedString(string, Style.EMPTY.withFormatting(Formatting.BLACK))
    );
    textBox.positioning(Positioning.absolute(98, 18));
    textBox.onChanged().subscribe(it -> {
      search = it.toLowerCase();
      updateRecipes();
    });
    container.child(textBox);

    container.child(
      new TexturedButton(
        it -> this.parent.toggleRecipeList(),
        RECIPES_SCREEN_TEXTURE,
        208, 0
      )
        .sizing(Sizing.fixed(16))
        .positioning(Positioning.absolute(44, 16))
        .tooltip(Text.translatable("block.minecells.cell_crafter.return"))
    );

    container.child(
      applyButton
        .sizing(Sizing.fixed(16))
        .positioning(Positioning.absolute(152, 138))
        .tooltip(Text.translatable("block.minecells.cell_crafter.select_recipe"))
    );

    container.child(upButton
      .sizing(Sizing.fixed(16))
      .positioning(Positioning.absolute(152, 36))
    );
    container.child(downButton
      .sizing(Sizing.fixed(16))
      .positioning(Positioning.absolute(152, 52))
    );

    container.child(label);

    grid.positioning(Positioning.absolute(43, 34)).allowOverflow(true);

    container.child(grid);
    rootComponent.child(container);

    var categoryContainer = Containers.verticalFlow(Sizing.fixed(48), Sizing.fixed(160));
    categoryContainer.allowOverflow(true).positioning(Positioning.absolute(0, 16));

    for (var category : CellForgeRecipe.Category.values()) {
      categoryContainer.child(new CategoryButton(category).sizing(Sizing.fixed(40), Sizing.fixed(25)).tooltip(category.getName()));
    }

    container.child(categoryContainer);

    updateButtons();
    updateSelectedRecipeDisplay();
  }

  void updateButtons() {
    upButton.active(scrollOffset > 0);
    downButton.active(scrollOffset < getMaxYOffset());
    applyButton.active(selectedRecipe != null);
  }

  public void updateRecipes(List<DisplayedRecipe> recipes) {
    allRecipes.clear();
    allRecipes.addAll(recipes);

    allRecipes.sort((a, b) -> {
      if (a.isUnlocked() && !b.isUnlocked()) return -1;
      if (!a.isUnlocked() && b.isUnlocked()) return 1;

      var priority = b.recipe().priority() - a.recipe().priority();
      if (priority != 0) return priority;

      var aName = a.recipe().output().getItem().getName().getString();
      var bName = b.recipe().output().getItem().getName().getString();

      return aName.compareTo(bName);
    });

    updateRecipes();
  }

  public void clearSearch() {
    search = "";
    textBox.setText("");
    updateRecipes();
  }

  public void updateRecipes() {
    currentRecipes.clear();
    visibleRecipes.clear();

    allRecipes.stream()
      .filter(it -> search.isBlank() || (
        it.isUnlocked && it.recipe().output().getItem().getName().getString().toLowerCase().contains(search))
      )
      .filter(it -> it.recipe().category() == selectedCategory)
      .forEach(currentRecipes::add);

    for (int i = scrollOffset * 6; i < currentRecipes.size(); ++i) {
      visibleRecipes.add(currentRecipes.get(i));
    }

    if (!currentRecipes.contains(selectedRecipe)) {
      selectedRecipe = null;
    }

    for (int r = 0; r < 5; ++r) {
      for (int c = 0; c < 6; ++c) {
        grid.removeChild(r, c);
        var index = r * 6 + c;
        if (index >= visibleRecipes.size()) {
          continue;
        }
        var recipe = visibleRecipes.get(index);
        var component = new RecipeComponent(recipe, MinecraftClient.getInstance().textRenderer, this)
          .margins(Insets.of(1));
        grid.child(component, r, c);
      }
    }

    label.text(selectedCategory.getName());

    updateButtons();
    updateSelectedRecipeDisplay();
  }

  private void updateSelectedRecipeDisplay() {
    if (container == null) {
      return;
    }

    if (ingredientDisplay != null && ingredientDisplay.parent() == container) {
      container.removeChild(ingredientDisplay);
    }

    if (selectedRecipe == null) {
      return;
    }

    ingredientDisplay = new CellCrafterScreen.IngredientDisplay(
      parent.getScreenHandler().player().getInventory(), selectedRecipe.recipe(),
      Sizing.fixed(96), Sizing.fixed(16)
    );
    ingredientDisplay.positioning(Positioning.absolute(50, 128));

    container.child(ingredientDisplay);
  }

  public void scrollDown() {
    var newYOffset = Math.min(getMaxYOffset(), scrollOffset + 1);
    if (newYOffset != scrollOffset) {
      scrollOffset = newYOffset;
      updateRecipes();
      updateButtons();
    }
  }

  public void scrollUp() {
    var newYOffset = Math.max(0, scrollOffset - 1);
    if (newYOffset != scrollOffset) {
      scrollOffset = newYOffset;
      updateRecipes();
      updateButtons();
    }
  }

  private int getMaxYOffset() {
    return Math.max(0, (int) Math.ceil(currentRecipes.size() / 6.0) - 5);
  }

  public record DisplayedRecipe(
    CellForgeRecipe recipe,
    boolean isUnlocked
  ) {
  }

  private static class RecipeComponent extends ItemComponent {
    private final DisplayedRecipe recipe;
    private final TextRenderer textRenderer;
    private final CellCrafterRecipeList parent;

    public RecipeComponent(DisplayedRecipe recipe, TextRenderer textRenderer, CellCrafterRecipeList parent) {
      super(recipe.recipe().output());
      this.recipe = recipe;
      this.textRenderer = textRenderer;
      this.parent = parent;
      this.showOverlay(true);

      if (recipe.isUnlocked) {
        this.mouseDownEvents.source().subscribe((mouseX, mouseY, button) -> {
          MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
          parent.selectedRecipe = this.recipe;
          parent.updateButtons();
          parent.updateSelectedRecipeDisplay();
          return true;
        });
        this.cursorStyle(CursorStyle.HAND);
      }

      this.sizing(Sizing.fixed(16));
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
      var matrices = context.getMatrices();

      if (recipe.isUnlocked) super.draw(context, mouseX, mouseY, partialTicks, delta);

      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();

      if (recipe.isUnlocked) {
        if (this.hovered || parent.selectedRecipe == this.recipe) {
          matrices.push();
          matrices.translate(0, 0, 200);
          context.drawTexture(RECIPES_SCREEN_TEXTURE, x() - 3, y() - 3, 160, 96, 22, 22);
          matrices.pop();
        }
        if (this.hovered) {
          context.drawItemTooltip(textRenderer, recipe.recipe().output(), mouseX, mouseY);
        }
      } else {
        matrices.push();
        matrices.translate(0, 0, 300);
        context.drawTexture(RECIPES_SCREEN_TEXTURE, x(), y(), 192, 64, 16, 16);
        matrices.pop();

        if (this.hovered) {
          var advancementKey = Util.createTranslationKey("advancements", recipe.recipe().requiredAdvancement().orElseThrow()) + ".description";
          var advancementName = Text.translatable(advancementKey).getString();
          context.drawOrderedTooltip(
            textRenderer,
            List.of(
              OrderedText.styledForwardsVisitedString("Locked", Style.EMPTY.withFormatting(Formatting.RED)),
              OrderedText.styledForwardsVisitedString(advancementName, Style.EMPTY.withFormatting(Formatting.GRAY))
            ),
            mouseX,
            mouseY
          );
        }
      }
      RenderSystem.disableDepthTest();
      RenderSystem.disableBlend();

    }
  }

  private class CategoryButton extends TexturedButton {

    public CategoryButton(CellForgeRecipe.Category category) {
      super(
        it -> {
          selectedCategory = category;
          scrollOffset = 0;
          clearSearch();
        },
        RECIPES_SCREEN_TEXTURE, 160, 0
      );

      this.renderer = (context, button, delta) -> {
        int renderV = 0;
        if (button.isHovered() || selectedCategory == category) {
          renderV += button.height();
        }

        var x = button.getX();
        if (selectedCategory != category) {
          x += 8;
        }

        var matrices = context.getMatrices();
        matrices.push();
        matrices.translate(0, 0, -3);

        RenderSystem.enableDepthTest();
        context.drawTexture(
          RECIPES_SCREEN_TEXTURE,
          x, button.getY(), 160, renderV,
          button.width(), button.height(),
          256, 256
        );

        context.drawItem(category.displayItem.getDefaultStack(), x + 12, y() + 6);
        matrices.pop();
      };
    }
  }

  //#region Absolutely disgusting workaround to disable shadows in the textbox
  private static class DisableShadowDrawContext extends DrawContext {
    public DisableShadowDrawContext(DrawContext parent) {
      super(MinecraftClient.getInstance(), parent.getVertexConsumers());
      this.getMatrices().multiplyPositionMatrix(parent.getMatrices().peek().getPositionMatrix());
    }

    @Override
    public int drawTextWithShadow(TextRenderer textRenderer, OrderedText text, int x, int y, int color) {
      return this.drawText(textRenderer, text, x, y, color, false);
    }
  }

  private static class NoShadowTextBox extends TextBoxComponent {
    protected NoShadowTextBox(Sizing horizontalSizing) {
      super(horizontalSizing);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
      super.renderButton(new DisableShadowDrawContext(context), mouseX, mouseY, delta);
    }
  }
  //#endregion
}
