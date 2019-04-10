
% 1
nB_BB = 33694;
nG_BB = 32193;
N_BB = nB_BB + nG_BB;

prb = 0.515;
prg = 0.485;

% What numbers would you get in an idealized experiment?
EnB_BB = prb * N_BB;
EnG_BB = prg * N_BB;

% What would the statistical model for this experiment be; if we test whether the gender
% of the third child is dependant on the genders of the two previous children?

% X ~ binomial(N, prb)

% Define the NULL hypothesis and the alternative Hypothesis

% H0:
% est_prb = prb
% H1:
% est_prb != prb

% Calculate the p-value based on a Binomial distribution.
%    note: why +/- diff ??
diff = abs(nB_BB - round(EnB_BB));
pval = 2 * min(binocdf(EnB_BB-diff, N_BB, prb), 1-binocdf(EnB_BB+diff, N_BB, prb));

% Can you with a significance level of 0.05, reject the NULL hypothesis?
if (pval > 0.05)
    sprintf("No")
else
    sprintf("Yes")
end

% Repeat the hypothesis test based on a normal approximation
z = (nB_BB - EnB_BB) / sqrt(EnB_BB * (1- prb));
pval_norm = 2 * min(normcdf(z), 1- normcdf(z));

% What is the estimator of p, and what is the variance of the estimator?
p_est = prb;
p_est_var = (1/N_BB) * (prb * (1-prb));

% Calculate the confidence intervals for the p value
k = norminv(0.975);
p_low = (EnB_BB + (k^2 / 2) - k * sqrt((EnB_BB * (N_BB-EnB_BB)/N_BB) + (k^2 / 4))) / (N_BB + k^2); 
p_high =(EnB_BB + (k^2 / 2) + k * sqrt((EnB_BB * (N_BB-EnB_BB)/N_BB) + (k^2 / 4))) / (N_BB + k^2); 

% 2

nB = [33694 26868 26741 28561 3615 2703 2708 2583 2644 2636 2646 3148 ...
      454 326 310 288 345 316 283 362 340 279 324 355 315 394 396 470];
nG = [32193 25264 25378 27068 3301 2593 2592 2517 2577 2552 2589 2928 ...
      429 324 309 285 337 292 290 352 302 280 278 348 300 345 360 420];
  
RejectHypothesis = zeros(1,length(nB));
for i = 1:length(nB)
    N = nB(i) + nG(i);
    EnB = N*prb;
    z = (nB(i)-EnB)/sqrt(N*prb*(1-prb));
    pval(i) = 2*min(normcdf(z),1-normcdf(z));
    RejectHypothesis(i) = pval(i) < 0.05;
end