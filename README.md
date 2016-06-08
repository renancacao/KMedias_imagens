# KMedias_imagens
Exemplo de algoritmo K-means para redução de coresde imagem.


EP de Inteligência Artificial.
Universidade de São Paulo.
EACH - SI

Gustavo Soriano
Renan Souza
Renato Calabrezi

Esse programa utiliza o algoritmo k-means para reduzir(ou clusterizar) as cores dos pxels de uma imagem.

O algoritmo aloca cada pixel em uma matrix tridimensional (R,G,B), define X centroides(E cores) aleatorios e cria X clusters de acordo com a dkistância de cada ponto para cada um desses centróides.
É feito então o ajuste recalculando os centróides de acodo com os pontos médios de cada cluster.
Quando o Erro Quadrático está dentro do valor esperado a operação é encerrada e as novas cores  são definidas.


Para testar, deixe na pasta raiz do projeto um arquivo "imagem.png".
