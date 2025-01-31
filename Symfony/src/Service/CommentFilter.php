<?php

namespace App\Service;

class CommentFilter
{
    private array $forbiddenWords = ['fuck', 'shit', 'asshole'];

    public function filter(string $comment): string
    {
        return str_ireplace($this->forbiddenWords, '***', $comment);
    }
}
