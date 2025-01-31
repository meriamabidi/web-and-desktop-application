<?php

namespace App\Form;

use App\Entity\Labo;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class LaboType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('bloc')
            ->add('mail')
            ->add('tel')
            ->add('med')
            ->add('photo', FileType::class, [
                'label' => 'image',
                'mapped' => false,
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '1024k',
                        'mimeTypes' => [
                            'image/gif',
                            'image/jpeg',
                            'image/png',
                            'image/jpg',
                        ],
                        'mimeTypesMessage' => 'image NON valide',
                    ])
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Labo::class,
        ]);
    }
}
