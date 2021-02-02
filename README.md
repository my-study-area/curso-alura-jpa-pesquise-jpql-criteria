# curso-alura-jpa-pesquise-jpql-criteria
Curso de Java e JPA: Pesquise com JPQL e Criteria

## Anotações
**Aula 01 - Relacionamentos bidirecionais Ver primeiro vídeo**
- Num relacionamento biderecional com @OneToMany e @ManyToOne devemos anotar com `@OneToMany(mappedBy = "conta")`
- O relacionamento com `mappedBy` é o lado fraco do relacionamento e o inverso é o lado forte.
- o relacionamento do lado inverso é opcional

**Aula 02 - Lidando com queries N+1**
- os relacionamentos `*ToMany` no JPA são preguiçosos (lazy load) por padrão. Observe o log abaixo:
```sql
#consultando uma entidade sem acessar os dados do relacionamento
Hibernate: 
    select
        conta0_.id as id1_2_,
        conta0_.agencia as agencia2_2_,
        conta0_.numero as numero3_2_,
        conta0_.saldo as saldo4_2_,
        conta0_.titular as titular5_2_ 
    from
        Conta conta0_
Titular: Maria
Agência: 9876
Número: 124512
Titular: Bruno
Agência: 6543
Número: 169878
```

```bash
# consultando uma entidade e acessando os dados do relacionamento
Hibernate: 
    select
        conta0_.id as id1_2_,
        conta0_.agencia as agencia2_2_,
        conta0_.numero as numero3_2_,
        conta0_.saldo as saldo4_2_,
        conta0_.titular as titular5_2_ 
    from
        Conta conta0_ 
    where
        conta0_.id>3
Titular: Maria
Agência: 9876
Número: 124512
Hibernate: 
    select
        movimentac0_.conta_id as conta_id6_3_0_,
        movimentac0_.id as id1_3_0_,
        movimentac0_.id as id1_3_1_,
        movimentac0_.conta_id as conta_id6_3_1_,
        movimentac0_.data as data2_3_1_,
        movimentac0_.descricao as descrica3_3_1_,
        movimentac0_.tipoMovimentacao as tipoMovi4_3_1_,
        movimentac0_.valor as valor5_3_1_ 
    from
        Movimentacao movimentac0_ 
    where
        movimentac0_.conta_id=?
Movimentacoes: []
Titular: Bruno
Agência: 6543
Número: 169878
Hibernate: 
    select
        movimentac0_.conta_id as conta_id6_3_0_,
        movimentac0_.id as id1_3_0_,
        movimentac0_.id as id1_3_1_,
        movimentac0_.conta_id as conta_id6_3_1_,
        movimentac0_.data as data2_3_1_,
        movimentac0_.descricao as descrica3_3_1_,
        movimentac0_.tipoMovimentacao as tipoMovi4_3_1_,
        movimentac0_.valor as valor5_3_1_ 
    from
        Movimentacao movimentac0_ 
    where
        movimentac0_.conta_id=?
Movimentacoes: [br.com.alura.jpa.modelo.Movimentacao@73ab3aac, br.com.alura.jpa.modelo.Movimentacao@cb0f763]
```
- podemos carregar os relacionamento de forma antecipada (eager) adicionando `fetch = FetchType.EAGER` no relacionamento. Ex: `@OneToMany(mappedBy = "conta", fetch = FetchType.EAGER)`.
Observer o log abaixo:
```sql
Hibernate: 
    select
        conta0_.id as id1_2_,
        conta0_.agencia as agencia2_2_,
        conta0_.numero as numero3_2_,
        conta0_.saldo as saldo4_2_,
        conta0_.titular as titular5_2_ 
    from
        Conta conta0_ 
    where
        conta0_.id>3
#todos os relacionamento são carregados antecipadamente, mesmo se as informações de movimentações não sejam utilizadas
Hibernate: 
    select
        movimentac0_.conta_id as conta_id6_3_0_,
        movimentac0_.id as id1_3_0_,
        movimentac0_.id as id1_3_1_,
        movimentac0_.conta_id as conta_id6_3_1_,
        movimentac0_.data as data2_3_1_,
        movimentac0_.descricao as descrica3_3_1_,
        movimentac0_.tipoMovimentacao as tipoMovi4_3_1_,
        movimentac0_.valor as valor5_3_1_ 
    from
        Movimentacao movimentac0_ 
    where
        movimentac0_.conta_id=?
Hibernate: 
    select
        movimentac0_.conta_id as conta_id6_3_0_,
        movimentac0_.id as id1_3_0_,
        movimentac0_.id as id1_3_1_,
        movimentac0_.conta_id as conta_id6_3_1_,
        movimentac0_.data as data2_3_1_,
        movimentac0_.descricao as descrica3_3_1_,
        movimentac0_.tipoMovimentacao as tipoMovi4_3_1_,
        movimentac0_.valor as valor5_3_1_ 
    from
        Movimentacao movimentac0_ 
    where
        movimentac0_.conta_id=?
Titular: Maria
Agência: 9876
Número: 124512
Movimentacoes: []
Titular: Bruno
Agência: 6543
Número: 169878
Movimentacoes: [br.com.alura.jpa.modelo.Movimentacao@5bdd5689, br.com.alura.jpa.modelo.Movimentacao@646811d6]
```
- para evitar o `N+1` devemos utilizar o `join` no JPQL. Ex: `select from Conta c join fetch c.movimentacoes` ou `select distinct c from Conta c left join fetch c.movimentacoes` para recebermos até as contas que não possuem movimentações.
- utilizamos `distinct` no JPQL para trazermos somentes os dados não repetidos. Ex: `select distinct c from Conta c left join fetch c.movimentacoes`
- o `N+1` é uma consequência de relacionamentos *toMany e não é um problema exclusivo do JPA,podendo ocorrer no JDBC também.
