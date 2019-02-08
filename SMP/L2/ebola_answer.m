population_size = 10000;
Population=rand(1,population_size);
Infected = zeros(population_size, 1);
Symptoms = zeros(population_size, 1);

for n=1:length(Population)
    if Population(n)<=0.2 %Infected and symptoms
        Infected(n)=1;
        Symptoms(n)=1;
    elseif 0.2<Population(n) && Population(n)<=0.5 %Symptoms but not infected
        Infected(n)=0;
        Symptoms(n)=1;
    elseif Population(n)>0.99 %Infected and no symptoms
        Infected(n)=1;
        Symptoms(n)=0;
    else %No symptoms and not infected
        Infected(n)=0;
        Symptoms(n)=0;
    end
end

est_infected = 0;
est_infected_nosymptoms = 0;

for n=1:population_size
    if Infected(n) == 1
        est_infected = est_infected +1;
        if Symptoms(n) == 0
            est_infected_nosymptoms = est_infected_nosymptoms +1;
        end
    end
end

p_est_infected = est_infected / population_size;
p_est_infected_nosymptoms = est_infected_nosymptoms / population_size;

p=1;
q=1;

for n=1:length(Population)
    if Symptoms(n)==1 %With symptoms
       Pinfected_sym(p)=Infected(n); %Infected (1) or not (0)
       p=p+1;
    else %Without symptoms
        Pinfected_nosym(q)=Infected(n); %Infected (1) or not (0)
        q=q+1;
    end
end

sims = 100;
Ninfected=0; %Number of simulations with at least one infected of 2x5 persons
for k=1:sims %Number of simulations
    Ri=randi(p-1,1,5); %Five random numbers up to the number of persons withs symptoms
    Rnoti=randi(q-1,1,5); %Five random numbers up to the number of persons without symptoms
    if isempty(find(Pinfected_sym(Ri)==1,1))==0 %At least one of five persons with symtoms are infected
        Ninfected=Ninfected+1;
    elseif isempty(find(Pinfected_nosym(Rnoti)==1,1))==0 %At least one of five persons without symptoms are infected
        Ninfected=Ninfected+1;
    end
end

Pr_min_1_infected=Ninfected/sims %Probability of at least one infected of 2x5 persons

