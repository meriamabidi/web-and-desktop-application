<?php

namespace App\Controller;

use App\Entity\Labo;
use App\Form\LaboType;
use App\Repository\LaboRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Knp\Component\Pager\PaginatorInterface;


use Dompdf\Dompdf;
use Dompdf\Options;


#[Route('/labo')]
class LaboController extends AbstractController
{

    #[Route('/pdf', name: 'labo_pdf', methods: ['GET','POST'])]
    public function usersDataDownload(LaboRepository $laboRepository): Response
        {  
            $data = $laboRepository->findAll();
            // On définit les options du PDF
            $pdfOptions = new Options();
            // Police par défaut
            $pdfOptions->set('defaultFont', 'Arial');
            $pdfOptions->setIsRemoteEnabled(true);
    
            // On instancie Dompdf
            $dompdf = new Dompdf($pdfOptions);
            $context = stream_context_create([
                'ssl' => [
                    'verify_peer' => FALSE,
                    'verify_peer_name' => FALSE,
                    'allow_self_signed' => TRUE
                ]
            ]);
            $dompdf->setHttpContext($context);
    
            // On génère le html
            $html = $this->renderView('labo/pdf.html.twig', [
                'labos' => $data,
            ]);
    
            $dompdf->loadHtml($html);
            $dompdf->setPaper('A4', 'portrait');
            $dompdf->render();
    
            // On génère un nom de fichier
            $fichier = 'labos.pdf';
    
            // On envoie le PDF au navigateur
            $dompdf->stream($fichier, [
                'Attachment' => true
            ]);
            
            return new Response();
        }

    #[Route('/', name: 'app_labo_index', methods: ['GET'])]
    public function index(
        LaboRepository $laboRepository,): Response {
        return $this->render('labo/index.html.twig', [
            'labos' => $laboRepository->findAll(),
        ]);
        
    }
    #[Route('/front', name: 'front_labo_index', methods: ['GET'])]
    public function front(
        LaboRepository $laboRepository,
        PaginatorInterface $paginator,
        Request $request
    ): Response {
        
        $data = $laboRepository->findAll();

        $labos = $paginator->paginate(
            $data,
            $request->query->getInt('page', 1),
            1
        );

        return $this->render('labo/affichage.html.twig', [
            'labos' => $labos,
        ]);
    }

    #[Route('/new', name: 'app_labo_new', methods: ['GET', 'POST'])]
    public function new(
        Request $request,
        LaboRepository $laboRepository,
        SluggerInterface $slugger
    ): Response {
        $labo = new Labo();
        $labo->setAverageRating(0.0);
        $form = $this->createForm(LaboType::class, $labo);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imgFile = $form->get('photo')->getData();

            if ($imgFile) {
                $originalFilename = pathinfo(
                    $imgFile->getClientOriginalName(),
                    PATHINFO_FILENAME
                );
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename =
                    $safeFilename .
                    '-' .
                    uniqid() .
                    '.' .
                    $imgFile->guessExtension();
                try {
                    $imgFile->move(
                        $this->getParameter('img_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                }
                $labo->setImg($newFilename);
            }
            $laboRepository->save($labo, true);

            return $this->redirectToRoute(
                'app_labo_index',
                [],
                Response::HTTP_SEE_OTHER
            );
        }

        return $this->renderForm('labo/new.html.twig', [
            'labo' => $labo,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_labo_show', methods: ['GET'])]
    public function show(Labo $labo): Response
    {
        return $this->render('labo/show.html.twig', [
            'labo' => $labo,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_labo_edit', methods: ['GET', 'POST'])]
    public function edit(
        Request $request,
        Labo $labo,
        LaboRepository $laboRepository,
        SluggerInterface $slugger
    ): Response {
        $form = $this->createForm(LaboType::class, $labo);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imgFile = $form->get('photo')->getData();

            if ($imgFile) {
                $originalFilename = pathinfo(
                    $imgFile->getClientOriginalName(),
                    PATHINFO_FILENAME
                );
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename =
                    $safeFilename .
                    '-' .
                    uniqid() .
                    '.' .
                    $imgFile->guessExtension();
                try {
                    $imgFile->move(
                        $this->getParameter('img_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                }
                $labo->setImg($newFilename);
            }
            $laboRepository->save($labo, true);

            return $this->redirectToRoute(
                'app_labo_index',
                [],
                Response::HTTP_SEE_OTHER
            );
        }

        return $this->renderForm('labo/edit.html.twig', [
            'labo' => $labo,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_labo_delete', methods: ['POST'])]
    public function delete(
        Request $request,
        Labo $labo,
        LaboRepository $laboRepository
    ): Response {
        if (
            $this->isCsrfTokenValid(
                'delete' . $labo->getId(),
                $request->request->get('_token')
            )
        ) {
            $laboRepository->remove($labo, true);
        }

        return $this->redirectToRoute(
            'app_labo_index',
            [],
            Response::HTTP_SEE_OTHER
        );
    }

    #[Route('/A/{id}', name: 'app_show_analyses', methods: ['GET'])]
    public function showAnalyses(Labo $labo): Response
    {
        return $this->render('labo/showAnalyses.html.twig', [
            'labo' => $labo,
        ]);
    }
}
