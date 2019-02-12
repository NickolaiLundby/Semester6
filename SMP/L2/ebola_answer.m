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

% #1 Infected and #2 Infected no symptoms
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

% #3 At least one infected of five with symptoms and five without symptoms
% Sorting with (Pinfectec_sym) and without (Pinfected_nosym) symptoms
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

% #5: Mean number of tests
Total_number_tests=0;
for n=1:100
    Place_Ebola=randi(10); %Ramdon place of infected patient = Number of tested persons neccessary
    Total_number_tests=Total_number_tests+Place_Ebola;
end
Mean_number_tests=Total_number_tests/100

% #7: Number of combinations of two patients out of ten
Combinations(1,:)=0; %Combination number one
Combinations(1,1)=1;
Combinations(1,2)=1;
r=2;
for n=1:1000
    Combinations(r,:)=0; %Combination number r
    N=randi(10,1,2);
    Match=0;
    if N(1)~=N(2) %The two random numbers (places in the row) should be different
        Combinations(r,N(1))=1;
        Combinations(r,N(2))=1;
        for k=1:r-1
            if Match==0 && Combinations(k,N(1))==Combinations(r,N(1)) && Combinations(k,N(2))==Combinations(r,N(2))
                Match=1; % The combination had occured allready (not a new combination)
            end
        end
        if Match==0
            r=r+1;
        end
    end  
end
Number_of_combinations=r-1

% #8: Selecting two infected out of ten
Counter_match=0;
Counter_trial=0;
N1=randi(10);
for n=1:10000
    N=randi(10,1,2);
    if N(1)~=N(2)%The places in the row should be different
        Counter_trial=Counter_trial+1;
        if Combinations(N1,N(1))==1 && Combinations(N1,N(2))==1 %We have selected the two infected pesons
            Counter_match=Counter_match+1;
        end
    end
end
Pr_match=Counter_match/Counter_trial