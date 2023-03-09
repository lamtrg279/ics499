package ics499.GalaxyGenerator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Universe")
public class Universe {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "universe", allocationSize = 1)
  private Integer universeId;
  @Transient
  private int seed;
  private GalaxyShape shape;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<StarSystem> starSystem = new ArrayList<StarSystem>();
  private Stack<String> names;
  @Transient
  private Random rand = new Random();

  public Universe(GalaxyShape shape, Random random, int size, int seed) {
    this.shape = shape;
    this.seed = seed;
    this.rand = random;
    this.names = new Stack<String>();
    generateNames(1000);
    this.universeId = rand.nextInt(9000) + 1000;
    for (int i = 0; i < size; i++) {
      starSystem.add(StarSystem.generate(this));
    }
  }

  public Universe() {
  }

  public String newName() {
    if (names.size() <= 2) {
      generateNames(1000);
    }
    return names.pop();
  }

  public static Universe generate(int randSeed, int gSize, GalaxyShape gShape) {
    // seed will be given value by input, eventually
    Random nRandom = new Random(randSeed);
    Universe u = new Universe(gShape, nRandom, gSize, randSeed);
    return u;

    /**
     * TODO
     */
  }

  private void generateNames(int number) {
    Random temp = new Random();
    this.rand = temp;
    String[] firstSylable = { "Ame", "Shi", "Kiin", "Kael", "Sal", "Sale", "Her", "Hur", "Hue", "New",
        "Old", "Bri", "Twi", "Kel", "Lit", "Le", "Lye", "Deep", "Dark", "Alt", "Ber", "Bres", "Sat", "Fal",
        "Ka", "Ca", "Sej", "Con" };
    String[] secondSylable = { "rash", "nar", "'kan", "kan", "sho", "del", "le", "la", "ta", "coru", "mel",
        "'coru", "sin", "shin", "ba", "sed", "dro" };
    String[] thirdSylable = { "shin", "shen", "dale", "dreg", "je", "se", "ae", "te", "to", "toe", "ri",
        "dro", "dra", "jra", "del", "rel", "ni" };

    for (int i = 0; i < number; i++) {
      int num = rand.nextInt(3);
      if (num == 0) {
        String name = firstSylable[rand.nextInt(firstSylable.length)];
        names.push(name);
      } else if (num == 1) {
        String name = firstSylable[rand.nextInt(firstSylable.length)]
            + secondSylable[rand.nextInt(secondSylable.length)];
        names.push(name);
      } else if (num == 2) {
        String name = firstSylable[rand.nextInt(firstSylable.length)]
            + secondSylable[rand.nextInt(secondSylable.length)] + thirdSylable[rand.nextInt(thirdSylable.length)];
        names.push(name);
      } else {
        System.out.println("Ranom number somehow out of range");
      }
    }
  }

  public Integer getUniverseId() {
    return this.universeId;
  }

  public void setUniverseId(Integer id) {
    this.universeId = id;
  }

  public GalaxyShape getShape() {
    return this.shape;
  }

  public void setShape(GalaxyShape shape) {
    this.shape = shape;
  }

  public List<StarSystem> getStarSystem() {
    return this.starSystem;
  }

  public void setStarSystem(List<StarSystem> starSystem) {
    this.starSystem = starSystem;
  }

  @Override
  public String toString() {
    return "Universe [universeId=" + universeId + ", seed=" + seed + ", shape=" + shape + ", starSystem=" + starSystem
        + ", names=" + names + "]";
  }

  public void addStarSystem(StarSystem starSystemToAdd) {
    this.starSystem.add(starSystemToAdd);
  }

}