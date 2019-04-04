height_data = [1.66, 1.69, 1.50, 1.80, 1.68, 1.64, 1.65, 1.70, 1.72, 1.67, 1.69, 1.68, 1.20, 1.90, 1.2];
n_samples = length(height_data);
mu_0 = 1.68;
sigma_0 = 0.2;
alpha = 0.05;

mu = mean(height_data);

% H0 : mu = mu_0
% H1 : mu != mu_0
% reject if pval < alpha

z = (mu - mu_0) / (sigma_0 / sqrt(n_samples));

pval = 2 * (1 - normcdf(abs(z)));

% pval > alpha, not rejected! with both alpha = 0.05 and 0.1

rep = 100;
n = 30;
p = zeros(1,rep);
for i=1:rep
    hd = sigma_0 * randn(1,n) + mu_0;
    mu_hat = mean(hd);
    z = (mu_hat - mu_0) / (sigma_0 / sqrt(n));
    p(i) = 2 * min(normcdf(z,0,1),1 - normcdf(z,0,1));
end

fn = sum(p < alpha);
Pfr = fn / rep;


new_mu_0 = 1.78;

p = zeros(1,rep);
for i=1:rep
    hd = sigma_0 * randn(1,n) + new_mu_0;
    mu_hat = mean(hd);
    z = (mu_hat - mu_0) / (sigma_0 / sqrt(n));
    p(i) = 2 * min(normcdf(z,0,1),1 - normcdf(z,0,1));
end

fn_new = sum(p < alpha);
Pfr_new = fn_new / rep;
