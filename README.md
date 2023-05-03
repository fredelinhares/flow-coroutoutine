# flow-coroutoutine

O código desse projeto é um simples exemplo de como utilizar o coroutines Flow para buscar e exibir dados em uma aplicação Android. A aplicação exibe uma lista contendo dados fictícios que são atualizados periodicamente, a cada segundo.

Abaixo segue uma descrição do funcionamento do código, começando pela definição dos modelos e das classes utilizadas na implementação:

FakeData - é uma classe que define um objeto de dados fictício com duas propriedades: id e nome.

ResultWrapper - é uma classe selada que encapsula o resultado de uma operação assíncrona. Ela possui três subclasses: Success, Error e Loading. Success representa o resultado de uma operação bem-sucedida, Error representa um erro ocorrido durante a operação e Loading representa o estado de carregamento.

SampleDataSource - é uma classe que simula a busca de dados de uma fonte externa. Ela implementa uma função fetchData() que retorna um objeto Flow<FakeData>. O Flow é uma coleção assíncrona reativa que permite emitir dados sequencialmente. Neste exemplo, a função fetchData() emite um objeto FakeData a cada segundo indefinidamente.

SampleRepository - é uma classe que encapsula o acesso aos dados fornecidos pelo SampleDataSource. Ela possui apenas uma função getData() que retorna o Flow<FakeData> fornecido pelo SampleDataSource.

SampleViewModel - é uma classe que gerencia o estado dos dados que serão exibidos na tela. Ela possui uma variável _data do tipo MutableStateFlow<ResultWrapper<List<FakeData>>> que armazena o resultado da operação assíncrona. A variável data é uma versão imutável da variável _data. A classe possui também a função fetchData() que é chamada no construtor da classe e é responsável por chamar a função getData() do SampleRepository. A operação assíncrona é executada dentro de um escopo de ViewModel (viewModelScope.launch) e os dados são adicionados a uma lista de FakeData. A lista é então transformada em uma ResultWrapper.Success e emitida para o Flow.

SampleViewModelFactory - é uma classe que cria uma instância da classe SampleViewModel e passa o SampleRepository como parâmetro.

Na classe MainActivity, a função setContent é chamada com um lambda que define a UI da aplicação. Neste lambda, a primeira coisa que é feita é instanciar o SampleDataSource, o SampleRepository e o SampleViewModel utilizando o viewModel() do Compose. Em seguida, é criado um Surface que ocupa toda a tela e chama a função Greeting passando como parâmetro o SampleViewModel.

A função Greeting é responsável por exibir a UI da aplicação com base no resultado da operação assíncrona. A função utiliza o collectAsState() para observar o Flow de dados do SampleViewModel. Quando o valor do ResultWrapper muda, o when verifica o tipo de resultado e atualiza a UI de acordo. Se o resultado for do tipo Loading, um  Circular ProgressIndicator é exibido no centro da tela para indicar que a operação de busca de dados está em andamento. Se o resultado for do tipoSuccess, a lista de dados é renderizada em um LazyColumn. Para cada item da lista, um Text é criado com as informações do objeto FakeData. Se o resultado for do tipo Error, uma mensagem de erro é exibida na tela com o motivo da falha.

Vale ressaltar que a função collectAsState() é utilizada para converter o Flow em um StateFlow, que é um tipo de fluxo que permite o armazenamento do estado atual do fluxo como um valor que pode ser acessado e observado por vários componentes da interface de usuário. Quando um novo valor é emitido pelo fluxo, a função collectAsState() atualiza automaticamente o valor do estado, e a interface de usuário é atualizada de acordo com o novo valor.

# OBS

1. Nesse exemplo, não me preocupei com questões relacionadas a arquitetura. Simplesmente estou focando numa simples demonstração no uso de Flow Coroutines.
2. O corrente exemplo demonstra o uso do Flow como hot flow, já que os dados são emitidos independentemente do momento em que alguém se inscreve no fluxo.
