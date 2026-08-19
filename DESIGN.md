# RAPS - APP · Diretrizes de Design

Este documento define a identidade visual oficial do RAPS - APP e deve ser consultado durante o desenvolvimento de novas telas. A interface deve transmitir acolhimento, saúde, tranquilidade, confiança, organização e modernidade, com tecnologia sem aparência fria, boa legibilidade e simplicidade.

## Princípios

- Criar uma experiência acolhedora, moderna, limpa, profissional e humana.
- Priorizar clareza, legibilidade e organização em ambientes de saúde.
- Evitar excesso de informação e tornar áreas importantes rapidamente identificáveis.
- Manter consistência para que telas criadas por diferentes integrantes pareçam parte do mesmo aplicativo.
- Não inventar funcionalidades que ainda não tenham sido especificadas.

## Paleta de cores oficial

### Background

| Token | Valor | Uso |
| --- | --- | --- |
| Bg Light | `#F2F6F5` · RGB `242, 246, 245` | Fundo geral das telas no modo claro |

### Surface

| Token | Valor | Uso |
| --- | --- | --- |
| Solid | `rgba(255, 255, 255, 0.86)` | Cards, containers e superfícies sobre o background |
| Glass | `rgba(255, 255, 255, 0.88)` | Superfícies com sensação de vidro e elementos flutuantes, quando apropriado |
| Surface 02 | `#EDF3F1` | Superfícies secundárias, áreas internas e agrupamentos |
| Surface 03 | `#E5EEEB` | Superfícies com contraste um pouco maior e separação visual de áreas |

### Brand

| Token | Valor | Uso |
| --- | --- | --- |
| Primary Soft | `#DCEFEB` | Fundos suaves da identidade, seleções, chips e destaques discretos |
| Primary | `#1F7A6C` | Cor principal: botões, ícones ativos, selecionados, indicadores e destaques |
| Primary Strong | `#125E53` | Estados fortes, contraste e estados pressionados |
| Accent | `#7A62D3` | Detalhes especiais e destaques secundários; não deve competir com Primary |
| Accent Soft | `#EEE9FF` | Fundos de elementos associados ao Accent, chips e indicadores suaves |

### Texto e borda

| Token | Valor | Uso |
| --- | --- | --- |
| Text Primary | `#17312D` | Títulos, textos principais e informações importantes |
| Text Secondary | `#6F837F` | Descrições, textos auxiliares, placeholders e labels secundárias |
| Border Default | `rgba(31, 76, 68, 0.12)` | Divisores, contornos, inputs e separadores discretos |

Bordas devem ser sutis e não competir visualmente com o conteúdo.

### Status

| Estado | Cor | Fundo suave |
| --- | --- | --- |
| Success | `#2B8A67` | `#DFF4E9` |
| Warning | `#BD7B24` | `#FFF0D4` |
| Danger | `#C9535F` | `#FCE3E6` |
| Info | `#347DA0` | `#E1F1F8` |

Use as cores de estado para sucesso, alertas, erros e informações, respectivamente. Sempre associe texto ou ícone ao estado; ele nunca deve depender exclusivamente da cor.

## Tipografia oficial

O RAPS - APP utiliza duas famílias tipográficas.

### Plus Jakarta Sans

É a fonte de identidade, títulos, headings e destaques. Os headings devem utilizar peso ExtraBold.

| Elemento | Peso | Tamanho de referência |
| --- | --- | --- |
| H1 | ExtraBold | 44px |
| H2 | ExtraBold | 26px |
| H3 | ExtraBold | 22px |
| H4 | ExtraBold | 21px |
| H5 | ExtraBold | 17px |

### Manrope

É a fonte de interface, textos funcionais e leitura.

| Estilo | Peso | Tamanho | Uso |
| --- | --- | --- | --- |
| Button | Bold | 14px | Botões |
| Regular 14 | Medium | 14px | Textos principais, descrições e leitura |
| Regular 12 | Medium | 12px | Textos auxiliares, labels e informações secundárias |
| Regular 10 | Medium | 10px | Legendas e detalhes não essenciais |
| Bold 18 | Bold | 18px | Informações importantes, valores e títulos internos |
| Bold 14 | Bold | 14px | Labels importantes, itens de listas e pequenos títulos |
| Bold 12 | Bold | 12px | Labels, chips, indicadores e informações compactas |

Na implementação Android, os valores acima devem ser convertidos adequadamente para as unidades tipográficas da plataforma, preservando a hierarquia visual. Evite textos muito pequenos para informações importantes.

## Hierarquia visual

Plus Jakarta Sans deve comunicar identidade e títulos; Manrope deve cuidar da interface e da leitura. Títulos não devem competir entre si. A composição deve manter bastante legibilidade, poucos elementos disputando atenção e acesso rápido às informações importantes. Informações clínicas ou operacionais jamais devem depender apenas de cor para serem compreendidas.

## Componentes

### Botão primário

- Fundo `#1F7A6C` e texto branco.
- Manrope Bold.
- Boa área de toque e cantos suavemente arredondados.
- Contraste elevado.
- Estado pressed/ativo pode utilizar `#125E53`.

### Botão secundário

Pode utilizar fundo Primary Soft `#DCEFEB` com texto Primary Strong `#125E53`, ou fundo transparente com contorno discreto, conforme a hierarquia da ação.

### Ações destrutivas

Utilize `#C9535F`. A aparência nunca deve ser igual à de uma ação principal positiva.

### Inputs

Inputs devem ter aparência limpa, label compreensível, borda discreta, fundo claro e textos em Manrope. Os estados de foco, erro e desabilitado devem ser claros. Não use apenas placeholder como identificação permanente do campo.

### Cards

Use superfícies claras, separação discreta do background, organização interna clara e pouca ou nenhuma sombra. Priorize espaçamento e hierarquia em vez de bordas pesadas.

### Chips e status

Utilize combinações Strong + Soft correspondentes: Success `#2B8A67` + `#DFF4E9`; Warning `#BD7B24` + `#FFF0D4`; Danger `#C9535F` + `#FCE3E6`; Info `#347DA0` + `#E1F1F8`. Sempre inclua texto ou ícone identificador.

### Ícones

Use um único padrão visual: ícones simples, de traço limpo, boa identificação, linguagem moderna e tamanhos consistentes. Evite misturar estilos diferentes.

## Espaçamento e composição

A interface deve respirar, com separação clara entre blocos, margens consistentes, alinhamentos precisos e organização por hierarquia. Não crie telas excessivamente compactas.

Como não foi definida uma escala oficial de espaçamento, radius ou sombras, estes valores não devem ser tratados como parte da identidade original.

### Recomendações a validar pela equipe

Qualquer escala de espaçamento, raio de canto ou sombra necessária à implementação deve ser proposta nesta etapa e validada pela equipe antes de se tornar padrão do produto.

## Acessibilidade

- Mantenha contraste adequado e áreas de toque confortáveis.
- Evite textos excessivamente pequenos.
- Não transmita estados exclusivamente por cores.
- Apresente feedback visual para ações.
- Diferencie claramente ações destrutivas.
- Evite interfaces visualmente sobrecarregadas.

## Direção estética

O RAPS - APP não deve parecer infantil, excessivamente corporativo, hospitalar e frio, futurista em excesso, carregado ou genérico.

O verde principal estabelece a relação com saúde, equilíbrio e confiança. O roxo deve funcionar como accent e trazer personalidade sem dominar a interface. Fundos levemente esverdeados substituem o branco puro como fundo dominante, reduzindo a sensação de uma interface clínica e fria.

## Regra final

Não altere as fontes oficiais, a paleta oficial ou a hierarquia visual definida. Não invente funcionalidades do sistema. Qualquer nova tela deve seguir este documento para manter uma identidade coesa e reconhecível.
