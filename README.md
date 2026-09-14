# RAPS - APP

> Aplicativo acadêmico para apoiar a organização e a digitalização dos processos internos da Rede de Atenção Psicossocial de Poços de Caldas.

## Contexto

O RAPS - APP é um projeto acadêmico de desenvolvimento mobile criado para atender à RAPS de Poços de Caldas — Rede de Atenção Psicossocial.

A rede reúne serviços relacionados ao atendimento em saúde mental do município, incluindo:

- CAPS Infantojuvenil;
- CAPS II Adulto;
- CAPS Álcool e Drogas;
- clínicas de internação para tratamento de abstinência;
- hospitais gerais que disponibilizam leitos para pacientes em surto psiquiátrico.

O projeto surgiu a partir de um levantamento de requisitos realizado junto à instituição. Atualmente, diversos processos são realizados por meio de documentos físicos e papel, sem um aplicativo ou sistema centralizado de apoio. Isso gera grande volume de documentos, dificuldade de organização e consulta, demora na localização de dados específicos e processos internos menos eficientes.

O objetivo do RAPS - APP é começar a digitalizar, organizar e simplificar esses processos, permitindo uma rotina mais ágil e eficiente para os profissionais.

## Funcionalidades inicialmente previstas

### Agenda

Módulo de agendamento para planejamento e acompanhamento das atividades realizadas em cada dia.

### Prontuários

Criação, edição e consulta de prontuários e informações relacionadas aos pacientes.

### Gestão de Funcionários

Cadastro e visualização dos profissionais e funcionários vinculados à instituição.

> Novas funcionalidades poderão ser definidas durante as próximas etapas do levantamento de requisitos.

## Público e instituição

O sistema é desenvolvido para auxiliar os serviços que integram a RAPS de Poços de Caldas, com foco na organização e digitalização dos processos internos da instituição. Não se trata, neste momento, de um aplicativo de uso direto do paciente.

## Tecnologias

- Android Studio
- Kotlin
- Android

## Como rodar o projeto

### Pré-requisitos

- [Android Studio](https://developer.android.com/studio) atualizado (Ladybug ou mais recente).
- Android SDK 36 instalado (Android Studio faz isso pelo **SDK Manager** no primeiro sync, caso ainda não esteja instalado).
- Não é necessário instalar JDK separado: use o JDK que já vem embutido no Android Studio (JBR), conforme o passo abaixo.

### Passo a passo

1. **Clone o repositório**

   ```bash
   git clone https://github.com/devluiizz/raps-app.git
   ```

2. **Abra no Android Studio**

   `File > Open...` e selecione a pasta do projeto clonado.

3. **Confira o Gradle JDK**

   Em `File > Settings > Build, Execution, Deployment > Build Tools > Gradle`, o campo **Gradle JDK** deve apontar para o JDK embutido do Android Studio (`jbr-21` ou equivalente). Se estiver apontando para outro JDK/JRE instalado na máquina, o sync pode falhar e o projeto inteiro aparece com erros de "unresolved reference" — troque para o JBR e sincronize novamente.

4. **Sincronize o projeto**

   `File > Sync Project with Gradle Files` (ou clique no ícone do elefante na barra de ferramentas). Na primeira vez, o Android Studio baixa as dependências do Compose/Kotlin — pode levar alguns minutos.

5. **Rode o app**

   Selecione o módulo `app` na barra de execução, escolha um emulador ou um aparelho físico conectado (com depuração USB ativada) e clique em **Run** (▶).

### Rodar pelo terminal (opcional)

```bash
./gradlew assembleDebug   # gera o APK de debug
./gradlew test            # roda os testes unitários
```

### Onde mexer em cores e fontes

O design system fica em `app/src/main/java/com/raps/app/ui/theme/` (`Color.kt`, `Type.kt`, `Theme.kt`), com um comentário de exemplo de uso em cada arquivo. As diretrizes oficiais de onde esses valores vêm estão em [DESIGN.md](DESIGN.md) — qualquer alteração de cor ou fonte deve ser validada lá antes de mudar o código.

## Equipe

- [Luiz Felipe De Oliveira](https://github.com/devluiizz)
- [Matheus José Gonçalves dos Reis](https://github.com/MatheusJGReis)
- [Rafael Nascimento Abelini](https://github.com/rafaelabelini)
- [Vitor Eduardo Felicio](https://github.com/VitorDev830)

O projeto está sendo desenvolvido em contexto acadêmico, como parte da disciplina de Desenvolvimento de Software para Dispositivos Móveis.

## Identidade visual

As diretrizes oficiais de cores, tipografia, componentes e acessibilidade estão reunidas em [DESIGN.md](DESIGN.md).

