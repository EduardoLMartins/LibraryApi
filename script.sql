select 
lv.id,
lv.id_autor,
aut.id,
aut.data_nascimento,
aut.nascionalidade,
aut.nome,
lv.data_publicacao,
lv.genero,
lv.isbn,
lv.preco,
lv.titulo
from livro lv join public.autor aut on aut.id=lv.id_autor
where lv.id=?