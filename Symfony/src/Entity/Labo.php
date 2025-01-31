<?php

namespace App\Entity;

use App\Repository\LaboRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: LaboRepository::class)]
class Labo
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Donner le Nom du Labo")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"le bloc est obligatoire")]
    #[Assert\Length(min:1,max:1,minMessage:" Bloc n'exist pas ",maxMessage:" Bloc n'exist pas ")]

    private ?string $bloc = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"E-mail est obligatoire")]
    #[Assert\Email(message:"E-mail est obligatoire")]

    private ?string $mail = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"Numero de telephone est obligatoire")]
    #[Assert\Length(max:8,min:8,maxMessage:"Numero indisponible",minMessage:"Numero indisponible")]
    #[Assert\Positive(message:"Erreur num negatif")]

    private ?int $tel = null;

    #[ORM\Column(length: 255)]
    //insertion imageee formulaire
    private ?string $img = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"le nom de medecin-chef est obligatoire")]
    #[Assert\Length(min:10,max:100,minMessage:"Nom NON valide ",maxMessage:"Nom NON valide")]

    private ?string $med = null;

    #[ORM\OneToMany(mappedBy: 'laboo', targetEntity: Analyse::class)]
    private Collection $analyses;

    #[ORM\OneToMany(mappedBy: 'labo', targetEntity: Rating::class)]
    private Collection $ratings;

    #[ORM\Column]
    private ?float $averageRating = null;

    public function __construct()
    {
        $this->analyses = new ArrayCollection();
        $this->ratings = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getBloc(): ?string
    {
        return $this->bloc;
    }

    public function setBloc(string $bloc): self
    {
        $this->bloc = $bloc;

        return $this;
    }

    public function getMail(): ?string
    {
        return $this->mail;
    }

    public function setMail(string $mail): self
    {
        $this->mail = $mail;

        return $this;
    }

    public function getTel(): ?int
    {
        return $this->tel;
    }

    public function setTel(int $tel): self
    {
        $this->tel = $tel;

        return $this;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): self
    {
        $this->img = $img;

        return $this;
    }

    public function getMed(): ?string
    {
        return $this->med;
    }

    public function setMed(string $med): self
    {
        $this->med = $med;

        return $this;
    }

    /**
     * @return Collection<int, Analyse>
     */
    public function getAnalyses(): Collection
    {
        return $this->analyses;
    }

    public function addAnalysis(Analyse $analysis): self
    {
        if (!$this->analyses->contains($analysis)) {
            $this->analyses->add($analysis);
            $analysis->setLaboo($this);
        }

        return $this;
    }
   
    public function removeAnalysis(Analyse $analysis): self
    {
        if ($this->analyses->removeElement($analysis)) {
            // set the owning side to null (unless already changed)
            if ($analysis->getLaboo() === $this) {
                $analysis->setLaboo(null);
            }
        }

        return $this;
    }
    public function __toString()
    {
        return $this->nom;
    }

    /**
     * @return Collection<int, Rating>
     */
    public function getRatings(): Collection
    {
        return $this->ratings;
    }

    public function addRating(Rating $rating): self
    {
        if (!$this->ratings->contains($rating)) {
            $this->ratings->add($rating);
            $rating->setLabo($this);
        }

        return $this;
    }

    public function removeRating(Rating $rating): self
    {
        if ($this->ratings->removeElement($rating)) {
            // set the owning side to null (unless already changed)
            if ($rating->getLabo() === $this) {
                $rating->setLabo(null);
            }
        }

        return $this;
    }

    

   public function setAverageRating(float $averageRating): self
    {
        $this->averageRating = $averageRating;
        return $this;
    }

    public function getAverageRating()
    {
        $total = 0;
        $count = count($this->ratings);

        foreach ($this->ratings as $rating) {
            $total += $rating->getValue();
        }

        if ($count > 0) {
            return round($total / $count, 1);
        } else {
            return 0;
        }
    }

}
